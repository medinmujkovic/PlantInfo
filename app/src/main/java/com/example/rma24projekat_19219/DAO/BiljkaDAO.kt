package com.example.rma24projekat_19219.DAO


import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.rma24projekat_19219.Biljka
import com.example.rma24projekat_19219.BiljkaBitmap


@Dao
interface BiljkaDAO {

    suspend fun saveBiljka(biljka: Biljka):Boolean
    suspend fun fixOfflineBiljka():Int
    suspend fun addImage(idBiljke: Long,Bitmap: Bitmap):Boolean
    @Query("SELECT * FROM Biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    suspend fun clearData()

    //Pomocne metode (Helper functions)//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBiljka(biljka: Biljka): Long
    @Update
    suspend fun updateBiljka(biljka: Biljka): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(biljkaBitmap: BiljkaBitmap): Long
    @Query("SELECT * FROM Biljka WHERE onlineChecked = 0")
    suspend fun getOfflineBiljkas(): List<Biljka>
    @Query("DELETE FROM Biljka")
    suspend fun clearBiljkas()

    @Query("DELETE FROM BiljkaBitmap")
    suspend fun clearBiljkaBitmaps()


}
