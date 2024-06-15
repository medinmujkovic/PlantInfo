package com.example.rma24projekat_19219.DAO

import android.graphics.Bitmap
import androidx.room.*
import com.example.rma24projekat_19219.Biljka
import com.example.rma24projekat_19219.BiljkaBitmap


@Dao
abstract class BiljkaDAO {

    fun saveBiljka(biljka: Biljka): Boolean {
        return insertBiljka(biljka) != -1L
    }

    suspend fun fixOfflineBiljka(fixData: suspend (Biljka) -> Biljka): Int {
        val offlineBiljkas = getOfflineBiljkas()
        var updatedCount = 0

        for (biljka in offlineBiljkas) {
            val updatedBiljka = fixData(biljka)
            if (biljka != updatedBiljka) {
                updatedBiljka.onlineChecked = true
                updateBiljka(updatedBiljka)
                updatedCount++
            }
        }
        return updatedCount
    }

    fun addImage(idBiljke: String, bitmap: Bitmap): Boolean {
        val biljka = getBiljkaById(idBiljke)
        if (biljka != null) {
            if (getImageById(idBiljke) == null) {
                insertImage(BiljkaBitmap(idBiljke, bitmap))
                return true
            }
        }
        return false
    }

    fun clearData() {
        clearBiljkas()
        clearBiljkaBitmaps()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBiljka(biljka: Biljka): Long

    @Update
    abstract fun updateBiljka(biljka: Biljka): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImage(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM Biljka WHERE id = :idBiljke LIMIT 1")
    abstract fun getBiljkaById(idBiljke: String): Biljka?

    @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke = :idBiljke LIMIT 1")
    abstract fun getImageById(idBiljke: String): BiljkaBitmap?

    @Query("SELECT * FROM Biljka WHERE onlineChecked = 0")
    abstract fun getOfflineBiljkas(): List<Biljka>

    @Query("SELECT * FROM Biljka")
    abstract fun getAllBiljkas(): List<Biljka>

    @Query("DELETE FROM Biljka")
    abstract fun clearBiljkas()

    @Query("DELETE FROM BiljkaBitmap")
    abstract fun clearBiljkaBitmaps()

}
