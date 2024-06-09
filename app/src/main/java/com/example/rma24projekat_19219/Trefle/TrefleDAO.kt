package com.example.rma24projekat_19219.Trefle


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.rma24projekat_19219.Biljka
import com.example.rma24projekat_19219.R
import com.example.rma24projekat_19219.Activities.MainActivity
import com.example.rma24projekat_19219.Types.KlimatskiTip
import com.example.rma24projekat_19219.Types.ProfilOkusaBiljke
import com.example.rma24projekat_19219.Types.Zemljiste
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class TrefleDAO (private val context: Context){

    private val baseUrl = "https://trefle.io/api/v1/"
    private val defaultBitmap: Bitmap = context.resources?.let { BitmapFactory.decodeResource(it, R.drawable.ic_launcher_background) } ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    interface TrefleApiService {
        @GET("plants")
        suspend fun getPlantsByFlowerColor(
            @Query("filter[flower_color]") flowerColor: String,
            @Query("token") apiKey: String = "WRsQtW2Qqa4PJm0-TE1QsLYxKqWFa286xG-tj-WdKL0"
        ): TrefleResponse

        @GET("plants/search")
        suspend fun searchPlantByLatinName(
            @Query("q") latinName: String,
            @Query("token") apiKey: String = "WRsQtW2Qqa4PJm0-TE1QsLYxKqWFa286xG-tj-WdKL0"
        ): TrefleResponse
    }

    data class TrefleResponse(
        val data: List<PlantData>
    )
    data class PlantData(
        val id: Int,
        val common_name: String?,
        val scientific_name: String,
        val family: Family?,
        val edible: Boolean,
        val image_url: String?,
        val main_species: MainSpecies?)
    data class Family(
        val name: String
    )
    data class MainSpecies(
        val specifications: Specifications?
    )
    data class Specifications(
        val toxicity: String?,
        val growth: Growth?
    )
    data class Growth(
        val soil_texture: List<String>?,
        val light: Int?,
        val atmospheric_humidity: Int?
    )

    private val apiService: TrefleApiService = retrofit.create(TrefleApiService::class.java)

    suspend fun getImage(biljka: Biljka): Bitmap {
        return try {
            val response = apiService.searchPlantByLatinName(biljka.naziv)
            val imageUrl = response.data.firstOrNull()?.image_url
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
            val response = apiService.searchPlantByLatinName(biljka.naziv, "WRsQtW2Qqa4PJm0-TE1QsLYxKqWFa286xG-tj-WdKL0")
            val plantData = response.data.firstOrNull()
            if (plantData != null) {
                if (plantData.family?.name != biljka.porodica) {
                    biljka.porodica = plantData.family?.name ?: biljka.porodica
                }

                if (!plantData.edible) {
                    biljka.jela = emptyList()
                    if (!biljka.medicinskoUpozorenje.contains("NIJE JESTIVO")) {
                        biljka.medicinskoUpozorenje += " NIJE JESTIVO"
                    }
                }

                val toxicity = plantData.main_species?.specifications?.toxicity
                if (toxicity != "none" && !biljka.medicinskoUpozorenje.contains("TOKSIČNO")) {
                    biljka.medicinskoUpozorenje += " TOKSIČNO"
                }

                val validSoilTextures = plantData.main_species?.specifications?.growth?.soil_texture ?: emptyList<Zemljiste>()
                biljka.zemljisniTipovi = biljka.zemljisniTipovi.filter { validSoilTextures.contains(it) }.toMutableList()
                validSoilTextures.forEach { soil ->
                    if (!biljka.zemljisniTipovi.contains(soil)) {
                        (biljka.zemljisniTipovi as MutableList<Zemljiste>).add(soil as Zemljiste)
                    }
                }

                val light = plantData.main_species?.specifications?.growth?.light ?: 0
                val humidity = plantData.main_species?.specifications?.growth?.atmospheric_humidity ?: 0

                biljka.klimatskiTipovi = biljka.klimatskiTipovi.filter { climateType ->
                    KlimatskiTip.entries.any { it == climateType && light in it.light && humidity in it.atmospheric_humidity }
                }.toMutableList()

                KlimatskiTip.entries.forEach { climateType ->
                    if (light in climateType.light && humidity in climateType.atmospheric_humidity && !biljka.klimatskiTipovi.contains(climateType)) {
                        (biljka.klimatskiTipovi as MutableList<KlimatskiTip>).add(climateType)
                    }
                }
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
                val response = apiService.getPlantsByFlowerColor(flowerColor)
                response.data.filter { plant ->
                    plant.common_name?.contains(substr, ignoreCase = true) == true || plant.scientific_name.contains(substr, ignoreCase = true)
                }.map { plantData ->
                    Biljka(
                        naziv = plantData.scientific_name,
                        porodica = plantData.family?.name ?: "",
                        medicinskoUpozorenje = plantData.main_species?.specifications?.toxicity?.let {
                            if (it != "none") "TOKSIČNO" else ""
                        } ?: "",
                        medicinskeKoristi = emptyList(),
                        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
                        jela = if (plantData.edible == false) emptyList() else listOf(),
                        klimatskiTipovi = emptyList(),
                        zemljisniTipovi = mutableListOf<Zemljiste>()
                    )
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

}