package com.example.rma24projekat_19219.DAO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rma24projekat_19219.Biljka
import com.example.rma24projekat_19219.BiljkaBitmap

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1)
@TypeConverters(ConvertBitmap::class)

abstract class BiljkaDatabase : RoomDatabase() {
    abstract fun biljkaDAO(): BiljkaDAO

    companion object {
        @Volatile private var instance: BiljkaDatabase? = null

        fun getInstance(context: Context): BiljkaDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BiljkaDatabase::class.java, "biljke-db"
                ).build().also { instance = it }
            }
    }
}
