package com.example.rma24projekat_19219

import com.example.rma24projekat_19219.Types.KlimatskiTip
import com.example.rma24projekat_19219.Types.MedicinskaKorist
import com.example.rma24projekat_19219.Types.ProfilOkusaBiljke
import com.example.rma24projekat_19219.Types.Zemljiste

data class Biljka(
    val naziv: String,
    val porodica: String,
    val medicinskoUpozorenje: String,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke,
    val jela: List<String>,
    val klimatskiTipovi: List<KlimatskiTip>,
    val zemljisniTipovi: List<Zemljiste>
)