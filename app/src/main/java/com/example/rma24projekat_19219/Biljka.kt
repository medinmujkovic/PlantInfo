package com.example.rma24projekat_19219

import com.example.rma24projekat_19219.Types.KlimatskiTip
import com.example.rma24projekat_19219.Types.MedicinskaKorist
import com.example.rma24projekat_19219.Types.ProfilOkusaBiljke
import com.example.rma24projekat_19219.Types.Zemljiste

data class Biljka(
    var naziv: String,
    var porodica: String,
    var medicinskoUpozorenje: String,
    var medicinskeKoristi: List<MedicinskaKorist>,
    var profilOkusa: ProfilOkusaBiljke,
    var jela: List<String>,
    var klimatskiTipovi: List<KlimatskiTip>,
    var zemljisniTipovi: List<Zemljiste>
)
