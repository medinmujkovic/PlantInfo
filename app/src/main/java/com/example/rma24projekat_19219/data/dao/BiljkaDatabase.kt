package com.example.rma24projekat_19219.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rma24projekat_19219.data.models.Biljka
import com.example.rma24projekat_19219.data.models.BiljkaBitmap
import com.example.rma24projekat_19219.data.models.convert.ConvertBitmap
import com.example.rma24projekat_19219.data.models.convert.KlimatskiTipConverter
import com.example.rma24projekat_19219.data.models.convert.ListKlimatskiTipConverter
import com.example.rma24projekat_19219.data.models.convert.ListMedicinskaKoristConverter
import com.example.rma24projekat_19219.data.models.convert.ListStringConverter
import com.example.rma24projekat_19219.data.models.convert.ListZemljisteConverter
import com.example.rma24projekat_19219.data.models.convert.MedicinskaKoristConverter
import com.example.rma24projekat_19219.data.models.convert.ZemljisteConverter

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
