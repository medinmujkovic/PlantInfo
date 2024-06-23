package com.example.rma24projekat_19219.viewmodel.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rma24projekat_19219.models.Biljka
import com.example.rma24projekat_19219.models.BiljkaBitmap
import com.example.rma24projekat_19219.viewmodel.convert.ConvertBitmap
import com.example.rma24projekat_19219.viewmodel.convert.KlimatskiTipConverter
import com.example.rma24projekat_19219.viewmodel.convert.ListKlimatskiTipConverter
import com.example.rma24projekat_19219.viewmodel.convert.ListMedicinskaKoristConverter
import com.example.rma24projekat_19219.viewmodel.convert.ListStringConverter
import com.example.rma24projekat_19219.viewmodel.convert.ListZemljisteConverter
import com.example.rma24projekat_19219.viewmodel.convert.MedicinskaKoristConverter
import com.example.rma24projekat_19219.viewmodel.convert.ZemljisteConverter

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

abstract class BiljkaRepository : RoomDatabase(){
    abstract fun biljkaDAO(): BiljkaDAO

    companion object {
        private var INSTANCE: BiljkaRepository? = null

        fun getInstance(context: Context): BiljkaRepository {
            if (INSTANCE == null) {
                synchronized(BiljkaRepository::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BiljkaRepository::class.java,
                "biljke-db"
            ).build()

    }

}
