package com.example.rma24projekat_19219.viewmodel.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rma24projekat_19219.models.Biljka
import com.example.rma24projekat_19219.models.BiljkaBitmap
import com.example.rma24projekat_19219.viewmodel.convertors.ConvertBitmap
import com.example.rma24projekat_19219.viewmodel.convertors.KlimatskiTipConverter
import com.example.rma24projekat_19219.viewmodel.convertors.ListKlimatskiTipConverter
import com.example.rma24projekat_19219.viewmodel.convertors.ListMedicinskaKoristConverter
import com.example.rma24projekat_19219.viewmodel.convertors.ListStringConverter
import com.example.rma24projekat_19219.viewmodel.convertors.ListZemljisteConverter
import com.example.rma24projekat_19219.viewmodel.convertors.MedicinskaKoristConverter
import com.example.rma24projekat_19219.viewmodel.convertors.ZemljisteConverter
import com.example.rma24projekat_19219.viewmodel.dao.BiljkaDAO

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1)
@TypeConverters(
    ConvertBitmap::class,
    MedicinskaKoristConverter::class,
    ListMedicinskaKoristConverter::class,
    ListKlimatskiTipConverter::class,
    KlimatskiTipConverter::class,
    ListZemljisteConverter::class,
    ZemljisteConverter::class,
    ListStringConverter::class,
)

abstract class BiljkaDatabase : RoomDatabase(){
    abstract fun biljkaDAO(): BiljkaDAO

    companion object {
        private var INSTANCE: BiljkaDatabase? = null

        fun getInstance(context: Context): BiljkaDatabase {
            if (INSTANCE == null) {
                synchronized(BiljkaDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BiljkaDatabase::class.java,
                "biljke-db"
            ).build()

    }

}
