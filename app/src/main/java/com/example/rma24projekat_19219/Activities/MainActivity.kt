package com.example.rma24projekat_19219.Activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rma24projekat_19219.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.nova_biljka_activity)



//        val mods = resources.getStringArray(R.array.Modovi)
//        val spinner = findViewById<Spinner>(R.id.modSpinner)
//        val recyclerView = findViewById<RecyclerView>(R.id.biljkeRV)
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter=RecyclerViewAdapterMedicinski(biljke);
//        recyclerView.scrollToPosition(R.id.biljkeRV);
//
//
//        if (spinner != null) {
//            val adapter = ArrayAdapter(this,
//                android.R.layout.simple_spinner_item, mods)
//            spinner.adapter = adapter
//
//            spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View, position: Int, id: Long) {
//                    when (position) {
//                        0 -> recyclerView.adapter = RecyclerViewAdapterMedicinski(biljke)
//                        1 -> recyclerView.adapter = RecyclerViewAdapterKuharski(biljke)
//                        2 -> recyclerView.adapter = RecyclerViewAdapterBotanicki(biljke)
//                    }
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//
//                }
//            }
//        }
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }


    }
}