package com.example.rma24projekat_19219.viewmodel.dao


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.rma24projekat_19219.R
import com.example.rma24projekat_19219.viewmodel.repository.api.TrefleApiService
import com.example.rma24projekat_19219.models.Biljka
import com.example.rma24projekat_19219.models.types.KlimatskiTip
import com.example.rma24projekat_19219.models.types.ProfilOkusaBiljke
import com.example.rma24projekat_19219.models.types.Zemljiste
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class TrefleDAO (){

    private lateinit var context: Context
    private val baseUrl = "http://trefle.io/api/v1/"
    private val defaultBitmap: Bitmap by lazy {
        createDefaultBitmap()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun setContext(context: Context) {
        this.context = context
    }

    private fun createDefaultBitmap(): Bitmap {
        return context.resources?.let { BitmapFactory.decodeResource(it, R.drawable.ic_launcher_background) }
            ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }


    private val apiService: TrefleApiService = retrofit.create(TrefleApiService::class.java)

    suspend fun getImage(biljka: Biljka): Bitmap {
        return try {
            val nazivBiljke = biljka.naziv
            val startIndex = nazivBiljke.indexOf("(") + 1
            val endIndex = nazivBiljke.indexOf(")")
            val latinskiNaziv = nazivBiljke.substring(startIndex, endIndex).toLowerCase().replace(" ", "-")

            println("apiservice ${apiService}")
            val response = apiService.getPlantById(latinskiNaziv)
            val imageUrl = response.data.image_url
            if (imageUrl != null) {
                val bitmap = downloadImage(imageUrl)
                bitmap ?: defaultBitmap
            } else {
                defaultBitmap
            }
        } catch (e: Exception) {
            e.printStackTrace()
            defaultBitmap
        }
    }

    private fun downloadImage(url: String): Bitmap? {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fixData(biljka: Biljka): Biljka {
        return try {
            val nazivBiljke = biljka.naziv
            val startIndex = nazivBiljke.indexOf("(") + 1
            val endIndex = nazivBiljke.indexOf(")")
            var latinskiNaziv = nazivBiljke.substring(startIndex, endIndex).toLowerCase().replace(" ", "-")

            val response = apiService.getPlantById(latinskiNaziv)
            println("API response data: ${response.data}")

            val plantData = response.data

            if (plantData != null) {
                if (plantData.family != biljka.porodica) {
                    biljka.porodica = plantData.family ?: ""
                }

                if (!plantData.edible!!) {
                    biljka.jela = emptyList()
                    if (!biljka.medicinskoUpozorenje.contains("NIJE JESTIVO")) {
                        biljka.medicinskoUpozorenje += " NIJE JESTIVO"
                    }
                }

                val toxicity = plantData.specifications?.toxicity
                if (toxicity != "none" && !biljka.medicinskoUpozorenje.contains("TOKSIČNO")) {
                    biljka.medicinskoUpozorenje += " TOKSIČNO"
                } else if (toxicity == null) {
                    biljka.medicinskoUpozorenje += " TOKSIČNO"
                }

                val validSoilTextures = plantData.growth?.soil_texture ?: emptyList<String>()
                val existingSoils = biljka.zemljisniTipovi.map { it.name }
                val newSoils = validSoilTextures.filterNot { existingSoils.contains(it) }
                val updatedSoils = biljka.zemljisniTipovi + newSoils.map { Zemljiste.valueOf(it) }
                biljka.zemljisniTipovi = updatedSoils

                val light = plantData.growth?.light ?: 0
                val humidity = plantData.growth?.atmospheric_humidity ?: 0
                val filteredClimateTypes = biljka.klimatskiTipovi.filter { climateType ->
                    light in climateType.light || humidity in climateType.atmospheric_humidity
                }.toMutableList()


                KlimatskiTip.values().forEach { climateType ->
                    if (light in climateType.light && humidity in climateType.atmospheric_humidity && !filteredClimateTypes.contains(climateType)) {
                        filteredClimateTypes.add(climateType)
                    }
                }
                biljka.klimatskiTipovi = filteredClimateTypes
            }
            biljka
        } catch (e: Exception) {
            e.printStackTrace()
            biljka
        }
    }

    suspend fun getPlantsWithFlowerColor(flowerColor: String, substr: String): List<Biljka> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPlantsByFlowerColor(flowerColor,substr)

                val filteredPlants = response.data.filter { plant ->
                    val commonNameMatches = plant.common_name?.contains(substr, ignoreCase = true) ?: false
                    val scientificNameMatches = plant.scientific_name.contains(substr, ignoreCase = true)
                    println("Filtering plant: ${plant.scientific_name}, matches: $commonNameMatches matches2: $scientificNameMatches")
                    commonNameMatches || scientificNameMatches
                }

                val mappedPlants = filteredPlants.map { plantData ->
                    Biljka(
                        naziv = plantData.scientific_name,
                        porodica = plantData.family ?: "",
                        medicinskoUpozorenje = plantData.specifications?.toxicity?.let {
                            if (it != "none") "TOKSIČNO" else ""
                        } ?: "",
                        medicinskeKoristi = emptyList(),
                        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
                        jela = if (plantData.edible == true) listOf() else emptyList(),
                        klimatskiTipovi = emptyList(),
                        zemljisniTipovi = mutableListOf()
                    )
                }
                println("API response data: ${response.data}")
                println("Mapped plants: $mappedPlants")

                mappedPlants
            } catch (e: Exception) {
                emptyList()
            }
        }
    }




}