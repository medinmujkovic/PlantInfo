package com.example.rma24projekat_19219.DAO

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rma24projekat_19219.Biljka
import com.example.rma24projekat_19219.BiljkaBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1)
@TypeConverters(ConvertBitmap::class)

abstract class BiljkaDatabase(private val context: Context) : RoomDatabase(),BiljkaDAO{
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
                "biljka_database"
            ).build()

    }
    override
    suspend fun saveBiljka(biljka: Biljka): Boolean {
        return withContext(Dispatchers.IO) {
            var db = BiljkaDatabase.getInstance(context)
            var biljke = db!!.biljkaDAO().insertBiljka(biljka) != -1L
            return@withContext biljke
        }
    }

    suspend fun fixOfflineBiljka(fixData: suspend (Biljka) -> Biljka): Int {
        return withContext(Dispatchers.IO) {
            val offlineBiljkas = biljkaDAO().getOfflineBiljkas()
            var updatedCount = 0

            for (biljka in offlineBiljkas) {
                val updatedBiljka = fixData(biljka)
                if (biljka != updatedBiljka) {
                    updatedBiljka.onlineChecked = true
                    biljkaDAO().updateBiljka(updatedBiljka)
                    updatedCount++
                }
            }
            updatedCount
        }
    }

    override
    suspend fun addImage(idBiljke: Long, bitmap: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            val biljka = biljkaDAO().getAllBiljkas().find { it.id == idBiljke }
            if (biljka != null) {
                val existingImage = biljkaDAO().getAllBiljkas().any { it.id == idBiljke }
                if (!existingImage) {
                    biljkaDAO().insertImage(BiljkaBitmap(idBiljke, bitmap))
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }

    override
    suspend fun getAllBiljkas(): List<Biljka> {
        return withContext(Dispatchers.IO) {
            biljkaDAO().getAllBiljkas()
        }
    }

    override
    suspend fun clearData() {
        withContext(Dispatchers.IO) {
            biljkaDAO().clearBiljkas()
            biljkaDAO().clearBiljkaBitmaps()
        }
    }

}
