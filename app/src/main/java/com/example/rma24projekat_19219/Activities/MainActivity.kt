package com.example.rma24projekat_19219.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rma24projekat_19219.Adapters.RecyclerViewAdapterBotanicki
import com.example.rma24projekat_19219.Adapters.RecyclerViewAdapterKuharski
import com.example.rma24projekat_19219.Adapters.RecyclerViewAdapterMedicinski
import com.example.rma24projekat_19219.R
import com.example.rma24projekat_19219.Trefle.TrefleDAO
import com.example.rma24projekat_19219.biljke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var biljkeRV: RecyclerView
    private lateinit var modSpinner: Spinner
    private lateinit var resetBtn: Button
    private lateinit var pretragaET: EditText
    private lateinit var bojaSPIN: Spinner
    private lateinit var brzaPretragaBtn: Button
    private lateinit var medicinskiAdapter: RecyclerViewAdapterMedicinski
    private lateinit var kuharskiAdapter: RecyclerViewAdapterKuharski
    private lateinit var botanickiAdapter: RecyclerViewAdapterBotanicki
    private lateinit var trefleDAO: TrefleDAO
    private lateinit var novaBiljkaBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trefleDAO = TrefleDAO()
        trefleDAO.setContext(this)

        enableEdgeToEdge()

        biljkeRV = findViewById(R.id.biljkeRV)
        modSpinner = findViewById(R.id.modSpinner)
        resetBtn = findViewById(R.id.resetBtn)
        pretragaET = findViewById(R.id.pretragaET)
        bojaSPIN = findViewById(R.id.bojaSPIN)
        brzaPretragaBtn = findViewById(R.id.brzaPretraga)
        biljkeRV.layoutManager = LinearLayoutManager(this)
        medicinskiAdapter = RecyclerViewAdapterMedicinski(biljke)
        kuharskiAdapter = RecyclerViewAdapterKuharski(biljke)
        botanickiAdapter = RecyclerViewAdapterBotanicki(biljke)
        setupSpinner()
        setupButtons()

        novaBiljkaBtn = findViewById(R.id.novaBiljkaBtn)
        novaBiljkaBtn.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSpinner() {
        val mods = resources.getStringArray(R.array.Modovi)
        ArrayAdapter(this, android.R.layout.simple_spinner_item, mods).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            modSpinner.adapter = this
        }
        modSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> biljkeRV.adapter = medicinskiAdapter
                    1 -> biljkeRV.adapter = kuharskiAdapter
                    2 -> biljkeRV.adapter = botanickiAdapter
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
    private fun setupButtons() {
        resetBtn.setOnClickListener {
            pretragaET.text.clear()
            bojaSPIN.setSelection(0)
        }
        val colors = listOf("red", "blue", "yellow", "orange", "purple", "brown", "green")
        val colorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colors)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bojaSPIN.adapter = colorAdapter

        brzaPretragaBtn.setOnClickListener {
            val selectedColor = bojaSPIN.selectedItem.toString()
            val query = pretragaET.text.toString()
            if (query.isNotEmpty() && colors.contains(selectedColor)) {
                GlobalScope.launch(Dispatchers.Main) {
                    try {

                        val plants = trefleDAO.getPlantsWithFlowerColor(selectedColor, query)
                        botanickiAdapter.updateBiljke(plants)
                    } catch (e: Exception) {
                        // Handle exception
                    }
                }
            }
        }
    }
}