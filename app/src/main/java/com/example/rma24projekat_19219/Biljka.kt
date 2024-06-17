package com.example.rma24projekat_19219

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rma24projekat_19219.Types.KlimatskiTip
import com.example.rma24projekat_19219.Types.MedicinskaKorist
import com.example.rma24projekat_19219.Types.ProfilOkusaBiljke
import com.example.rma24projekat_19219.Types.Zemljiste
import com.google.gson.annotations.SerializedName

@Entity
data class Biljka(
    @PrimaryKey (autoGenerate = true) @SerializedName("id") var id:Long?=null,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") var naziv: String,
    @ColumnInfo(name = "porodica") @SerializedName("porodica") var porodica: String,
    @ColumnInfo(name = "medicinskoUpozorenje") @SerializedName("medicinskoUpozorenje") var medicinskoUpozorenje: String,
    @ColumnInfo(name = "medicinskeKoristi") @SerializedName("medicinskeKoristi") var medicinskeKoristi: List<MedicinskaKorist>,
    @ColumnInfo(name = "profilOkusa") @SerializedName("profilOkusa") var profilOkusa: ProfilOkusaBiljke,
    @ColumnInfo(name = "jela") @SerializedName("jela") var jela: List<String>,
    @ColumnInfo(name = "klimatskiTipovi")  @SerializedName("klimatskiTipovi") var klimatskiTipovi: List<KlimatskiTip>,
    @ColumnInfo(name = "zemljisniTipovi") @SerializedName("zemljisniTipovi") var zemljisniTipovi: List<Zemljiste>,
    @ColumnInfo(name = "onlineChecked") @SerializedName("onlineChecked") var onlineChecked: Boolean = false
)

@Entity
data class BiljkaBitmap(
    @PrimaryKey (autoGenerate = true) val idBiljke:Long?=null,
    val bitmap: Bitmap
)