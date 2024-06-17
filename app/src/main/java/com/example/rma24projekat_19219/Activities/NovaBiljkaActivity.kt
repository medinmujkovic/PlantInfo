package com.example.rma24projekat_19219.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rma24projekat_19219.Biljka
import com.example.rma24projekat_19219.DAO.API.TrefleDAO
import com.example.rma24projekat_19219.R
import com.example.rma24projekat_19219.Types.KlimatskiTip
import com.example.rma24projekat_19219.Types.MedicinskaKorist
import com.example.rma24projekat_19219.Types.ProfilOkusaBiljke
import com.example.rma24projekat_19219.Types.Zemljiste
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NovaBiljkaActivity : AppCompatActivity() {

    private lateinit var jelaList: MutableList<String>
    private lateinit var jelaAdapter: ArrayAdapter<String>
    private lateinit var jeloET: EditText
    private lateinit var dodajJeloBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var biljke: MutableList<Biljka>
    private lateinit var dodajBiljkuBtn: Button
    private lateinit var nazivET: EditText
    private lateinit var porodicaET: EditText
    private lateinit var medicinskoUpozorenjeET: EditText
    private lateinit var medicinskaKoristLV: ListView
    private lateinit var klimatskiTipLV: ListView
    private lateinit var zemljisniTipLV: ListView
    private lateinit var profilOkusaLV: ListView
    private lateinit var slikaIV: ImageView
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var trefleDAO: TrefleDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_biljka_activity)

        trefleDAO = TrefleDAO()
        trefleDAO.setContext(this)

        enableEdgeToEdge()


        /*************Korist***********/
        medicinskaKoristLV = findViewById<ListView>(R.id.medicinskaKoristLV)
        val medicinskaKoristValues = MedicinskaKorist.entries.map { it.opis }
        val medicinskaKoristAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            medicinskaKoristValues
        )
        medicinskaKoristLV.adapter = medicinskaKoristAdapter
        medicinskaKoristLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        klimatskiTipLV = findViewById<ListView>(R.id.klimatskiTipLV)
        val klimatskiTipValues = KlimatskiTip.entries.map { it.opis }
        val klimatskiTipAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            klimatskiTipValues
        )
        klimatskiTipLV.adapter = klimatskiTipAdapter
        klimatskiTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        zemljisniTipLV = findViewById<ListView>(R.id.zemljisniTipLV)
        val zemljisniTipValues = Zemljiste.entries.map { it.naziv }
        val zemljisniTipAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            zemljisniTipValues
        )
        zemljisniTipLV.adapter = zemljisniTipAdapter
        zemljisniTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE


        /*************Profil Okusa***********/
        val profilOkusaLV = findViewById<ListView>(R.id.profilOkusaLV)
        val profilOkusaValues = ProfilOkusaBiljke.entries.map { it.opis }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, profilOkusaValues)
        profilOkusaLV.adapter = adapter
        profilOkusaLV.choiceMode = ListView.CHOICE_MODE_SINGLE

        /*************Jelo***********/
        jelaList = mutableListOf()
        jelaAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, jelaList)

        val jelaLV = findViewById<ListView>(R.id.jelaLV)
        jelaLV.adapter = jelaAdapter
        jelaLV.choiceMode = ListView.CHOICE_MODE_SINGLE

        /*************Dodaj jelo***********/
        jeloET = findViewById(R.id.jeloET)
        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)
        jelaLV.visibility = ListView.VISIBLE

        dodajJeloBtn.setOnClickListener {
            val novoJelo = jeloET.text.toString().trim()
            if (novoJelo.isNotEmpty()) {
                val postojeceJelo = jelaList.find { it.equals(novoJelo, ignoreCase = true) }
                if (postojeceJelo != null) {
                    val index = jelaList.indexOf(postojeceJelo)
                    jelaList[index] = novoJelo
                } else {
                    jelaList.add(novoJelo)
                }
                if (jelaList.contains(novoJelo)) {
                    val i = jelaList.indexOf(novoJelo)
                    jelaList[i] = novoJelo
                } else {
                    jelaList.add(novoJelo)
                }
                jelaAdapter.notifyDataSetChanged()
                jeloET.setText("")
                dodajJeloBtn.text = "Dodaj jelo"
            }
        }

        jelaLV.setOnItemClickListener { _, _, position, _ ->
            jeloET.setText(jelaList[position])
            dodajJeloBtn.text = "Izmijeni jelo"
        }

        /*************Recycle view: dodajBiljkuBtn***********/
//        val recyclerView = findViewById<RecyclerView>(R.id.biljkeRV)
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter= RecyclerViewAdapterMedicinski(com.example.rma24projekat_19219.biljke);
//        recyclerView.scrollToPosition(R.id.biljkeRV);

        dodajBiljkuBtn = findViewById(R.id.dodajBiljkuBtn)
        nazivET = findViewById(R.id.nazivET)
        porodicaET = findViewById(R.id.porodicaET)
        medicinskoUpozorenjeET = findViewById(R.id.medicinskoUpozorenjeET)

        dodajBiljkuBtn.setOnClickListener {
            val naziv = nazivET.text.toString().trim()
            val porodica = porodicaET.text.toString().trim()
            val medicinskoUpozorenje = medicinskoUpozorenjeET.text.toString().trim()
            val medicinskaKoristt = getMedicinskaKorist(medicinskaKoristLV)
            val klimatskiTipp = getKlimatskiTip(klimatskiTipLV)
            val zemljisniTipp = getZemljisniTip(zemljisniTipLV)
            val odabraniProfilOkusa = getOdabraniProfilOkusa();
            val jelaa = getJela(jelaLV);

            if (validateFields(naziv, porodica, medicinskoUpozorenje, medicinskaKoristt,
                    odabraniProfilOkusa,jelaa,klimatskiTipp,zemljisniTipp)) {
                val novaBiljka = Biljka(
                    naziv=naziv,
                    porodica=porodica,
                    medicinskoUpozorenje=medicinskoUpozorenje,
                    medicinskeKoristi = medicinskaKoristt,
                    profilOkusa = odabraniProfilOkusa,
                    jela=jelaa,
                    klimatskiTipovi = klimatskiTipp,
                    zemljisniTipovi = zemljisniTipp)
                biljke.add(novaBiljka)
                recyclerView.adapter?.notifyItemInserted(biljke.size - 1)
                recyclerView.scrollToPosition(biljke.size - 1)
                adapter.notifyDataSetChanged()
                clearFields()
                prikaziToast("Nova biljka dodana")

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("biljkeList", biljke.toTypedArray())
                startActivity(intent)
                onBackPressed()

                GlobalScope.launch(Dispatchers.IO) {
                    val ispravljenaBiljka = trefleDAO.fixData(novaBiljka)
                    withContext(Dispatchers.Main) {
                        biljke.add(ispravljenaBiljka)
                    }
                }
            }
        }

        /*************Intent: uslikajBiljkuBtn***********/
        slikaIV = findViewById(R.id.slikaIV)
        val uslikajBiljkuBtn = findViewById<Button>(R.id.uslikajBiljkuBtn)

        uslikajBiljkuBtn.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    /********validacija*****/
    private fun validateFields(
        naziv: String,
        porodica: String,
        medicinskoUpozorenje: String,
        medicinskaKoristt: List<MedicinskaKorist>,
        profilOkusa: ProfilOkusaBiljke,
        jelaa: List<String>,
        klimatskiTipp: List<KlimatskiTip>,
        zemljisniTipp: List<Zemljiste>
    ): Boolean {
        if (naziv.length < 3 || naziv.length > 20) {
            nazivET.error = "Naziv mora biti između 3 i 20 znakova"
            return false
        }
        if (porodica.length < 3 || porodica.length > 20) {
            porodicaET.error = "Porodica mora biti između 3 i 20 znakova"
            return false
        }
        if (medicinskoUpozorenje.length < 3 || medicinskoUpozorenje.length > 20) {
            medicinskoUpozorenjeET.error = "Medicinsko upozorenje mora biti između 3 i 20 znakova"
            return false
        }
        if (profilOkusa == null) {
            prikaziToast("Odaberite profil okusa")
            return false
        }
        if (medicinskaKoristt.isEmpty()) {
            prikaziToast("Odaberite medicinsku korist")
            return false
        }
        if (jelaa.isEmpty()) {
            prikaziToast("Dodajte jelo")
            return false
        }
        if (klimatskiTipp.isEmpty()) {
            prikaziToast("Odaberite klimatski tip")
            return false
        }
        if (zemljisniTipp.isEmpty()) {
            prikaziToast("Odaberite zemljisni tip")
            return false
        }
        return true
    }


    /********selected*****/
    private fun getOdabraniProfilOkusa(): ProfilOkusaBiljke {
        val odabraniIndex = profilOkusaLV.checkedItemPosition
        return when (odabraniIndex) {
            0 -> ProfilOkusaBiljke.MENTA
            1 -> ProfilOkusaBiljke.CITRUSNI
            2 -> ProfilOkusaBiljke.SLATKI
            3 -> ProfilOkusaBiljke.BEZUKUSNO
            4 -> ProfilOkusaBiljke.LJUTO
            5 -> ProfilOkusaBiljke.KORIJENASTO
            6 -> ProfilOkusaBiljke.AROMATICNO
            7 -> ProfilOkusaBiljke.GORKO
            else -> throw IllegalArgumentException("Nepoznat profil okusa")
        }
    }

    private fun getJela(listView: ListView): List<String> {
        val selectedItems = mutableListOf<String>()

        val checkedPositions = listView.checkedItemPositions
        for (i in 0 until checkedPositions.size()) {
            val position = checkedPositions.keyAt(i)
            if (checkedPositions.get(position)) {
                val selectedItem = listView.getItemAtPosition(position) as String
                selectedItems.add(selectedItem)
            }
        }

        return selectedItems
    }


    private fun getMedicinskaKorist(listView: ListView): List<MedicinskaKorist> {
        val selectedItems = mutableListOf<MedicinskaKorist>()
        val checkedItemPositions = listView.checkedItemPositions
        for (i in 0 until checkedItemPositions.size()) {
            val position = checkedItemPositions.keyAt(i)
            if (checkedItemPositions.valueAt(i)) {
                val selectedItem = listView.getItemAtPosition(position)
                selectedItems.add(selectedItem as MedicinskaKorist)
            }
        }
        return selectedItems
    }

    private fun getZemljisniTip(listView: ListView): List<Zemljiste> {
        val selectedItems = mutableListOf<Zemljiste>()
        val checkedItemPositions = listView.checkedItemPositions
        for (i in 0 until checkedItemPositions.size()) {
            val position = checkedItemPositions.keyAt(i)
            if (checkedItemPositions.valueAt(i)) {
                val selectedItem = listView.getItemAtPosition(position)
                selectedItems.add(selectedItem as Zemljiste)
            }
        }
        return selectedItems
    }

    private fun getKlimatskiTip(listView: ListView): List<KlimatskiTip> {
        val selectedItems = mutableListOf<KlimatskiTip>()
        val checkedItemPositions = listView.checkedItemPositions
        for (i in 0 until checkedItemPositions.size()) {
            val position = checkedItemPositions.keyAt(i)
            if (checkedItemPositions.valueAt(i)) {
                val selectedItem = listView.getItemAtPosition(position)
                selectedItems.add(selectedItem as KlimatskiTip)
            }
        }
        return selectedItems
    }


    private fun clearFields() {
        nazivET.setText("")
        porodicaET.setText("")
        medicinskoUpozorenjeET.setText("")
        profilOkusaLV.clearChoices()
    }

    private fun prikaziToast(poruka: String) {
        Toast.makeText(this, poruka, Toast.LENGTH_SHORT).show()
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            slikaIV.setImageBitmap(imageBitmap)
        }
    }
}

