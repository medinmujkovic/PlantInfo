package com.example.rma24projekat_19219

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rma24projekat_19219.models.Biljka
import com.example.rma24projekat_19219.viewmodel.dao.BiljkaDAO
import com.example.rma24projekat_19219.viewmodel.repository.BiljkaDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var biljkaDAO: BiljkaDAO
    private lateinit var db: BiljkaDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BiljkaDatabase::class.java).build()
        biljkaDAO = db.biljkaDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addBiljkaAndVerify() {
        val biljka = Biljka(
            id = 1L,
            naziv = "Test Biljka",
            porodica = "Test Family",
            medicinskoUpozorenje = "None",
            profilOkusa = null,
            jela = listOf("Dish1", "Dish2"),
            klimatskiTipovi = listOf(),
            zemljisniTipovi = listOf(),
            medicinskeKoristi = listOf()
        )

        runBlocking {
            biljkaDAO.saveBiljka(biljka)
            val allBiljkas = biljkaDAO.getAllBiljkas()
            assertEquals(1, allBiljkas.size)
            assertEquals(biljka.naziv, allBiljkas[0].naziv)
        }
    }

    @Test
    @Throws(Exception::class)
    fun clearDataAndVerify() {
        runBlocking {
            biljkaDAO.clearData()
            val allBiljkas = biljkaDAO.getAllBiljkas()
            assertTrue(allBiljkas.isEmpty())
        }
    }
}
