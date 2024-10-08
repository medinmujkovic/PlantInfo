package com.example.rma24projekat_19219.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.rma24projekat_19219.models.types.KlimatskiTip
import com.example.rma24projekat_19219.models.types.MedicinskaKorist
import com.example.rma24projekat_19219.models.types.ProfilOkusaBiljke
import com.example.rma24projekat_19219.models.types.Zemljiste
import com.example.rma24projekat_19219.viewmodel.convertors.ConvertBitmap
import com.example.rma24projekat_19219.viewmodel.convertors.ListKlimatskiTipConverter
import com.example.rma24projekat_19219.viewmodel.convertors.ListMedicinskaKoristConverter
import com.example.rma24projekat_19219.viewmodel.convertors.ListStringConverter
import com.example.rma24projekat_19219.viewmodel.convertors.ListZemljisteConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Biljka")

data class Biljka(
    @PrimaryKey (autoGenerate = true) @SerializedName("id") var id:Long?=null,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") var naziv: String,
    @ColumnInfo(name = "family") @SerializedName("family") var porodica: String,
    @ColumnInfo(name = "medicinskoUpozorenje") @SerializedName("medicinskoUpozorenje") var medicinskoUpozorenje: String,
    @ColumnInfo(name = "profilOkusa") @SerializedName("profilOkusa") val profilOkusa: ProfilOkusaBiljke?,
    @TypeConverters(ListStringConverter::class)
    @ColumnInfo(name = "jela") @SerializedName("jela") var jela: List<String>,
    @TypeConverters(ListKlimatskiTipConverter::class)
    @ColumnInfo(name = "klimatskiTipovi")  @SerializedName("klimatskiTipovi") var klimatskiTipovi: List<KlimatskiTip>,
    @TypeConverters(ListZemljisteConverter::class)
    @ColumnInfo(name = "zemljisniTipovi") @SerializedName("zemljisniTipovi") var zemljisniTipovi: List<Zemljiste>,
    @TypeConverters(ListMedicinskaKoristConverter::class)
    @ColumnInfo(name = "medicinskeKoristi") @SerializedName("medicinskeKoristi") var medicinskeKoristi: List<MedicinskaKorist>,
    @ColumnInfo(name = "onlineChecked") @SerializedName("onlineChecked") var onlineChecked: Boolean = false
)

@Entity
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idBiljke") val idBiljke: Long?=null,
    @TypeConverters(ConvertBitmap::class)
    @ColumnInfo(name = "bitmap") @SerializedName("naziv") val bitmap: Bitmap
)