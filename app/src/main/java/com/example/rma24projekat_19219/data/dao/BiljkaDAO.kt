package com.example.rma24projekat_19219.data.dao


import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.rma24projekat_19219.data.models.Biljka
import com.example.rma24projekat_19219.data.models.BiljkaBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Dao
interface BiljkaDAO {

    @Transaction
    suspend fun saveBiljka(biljka: Biljka): Boolean {
        return withContext(Dispatchers.IO) {
            try{
                insertBiljka(biljka)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    @Transaction
    suspend fun fixOfflineBiljka(): Int {
        return withContext(Dispatchers.IO) {
            val trefleDAO = TrefleDAO()
            val offlineBiljkas = getOfflineBiljkas()
            var updatedCount = 0

            for (biljka in offlineBiljkas) {
                try {
                    val originalBiljka = biljka.copy()
                    val updatedBiljka = withContext(Dispatchers.IO) {
                        trefleDAO.fixData(originalBiljka) // Assuming fixData is a suspend function
                    }
                    if (updatedBiljka != originalBiljka) {
                        updatedBiljka.onlineChecked = true
                        updateBiljka(updatedBiljka)
                        updatedCount++
                    }
                } catch (e: Exception) {

                }
            }
            updatedCount
        }
    }

    @Transaction
    suspend fun addImage(idBiljke: Long, bitmap: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            val biljka = getBiljkaById(idBiljke)
            if (biljka != null) {
                val existingBitmap = getBiljkaBitmapById(idBiljke)
                if (existingBitmap != null) return@withContext false

                val biljkaBitmap = BiljkaBitmap(idBiljke, bitmap)
                val result = insertBiljkaBitmap(biljkaBitmap)
                return@withContext result != -1L
            }
            return@withContext false
        }

    }

    @Query("SELECT * FROM Biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Transaction
    suspend fun clearData() {
        withContext(Dispatchers.IO) {
            clearBiljkas()
            clearBiljkaBitmaps()
        }
    }

    //Pomocne metode (Helper functions)//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBiljka(biljka: Biljka)
    @Update
    suspend fun updateBiljka(biljka: Biljka)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(biljkaBitmap: BiljkaBitmap): Long
    @Query("SELECT * FROM Biljka WHERE onlineChecked = 0")
    suspend fun getOfflineBiljkas(): List<Biljka>
    @Query("DELETE FROM Biljka")
    suspend fun clearBiljkas()

    @Query("DELETE FROM BiljkaBitmap")
    suspend fun clearBiljkaBitmaps()

    @Query("SELECT * FROM Biljka WHERE id = :idBiljke")
    suspend fun getBiljkaById(idBiljke: Long): Biljka?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBiljkaBitmap(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke = :idBiljke")
    suspend fun getBiljkaBitmapById(idBiljke: Long): BiljkaBitmap?

}
