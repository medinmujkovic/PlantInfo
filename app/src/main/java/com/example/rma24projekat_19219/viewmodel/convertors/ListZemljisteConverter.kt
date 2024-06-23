package com.example.rma24projekat_19219.viewmodel.convertors

import androidx.room.TypeConverter
import com.example.rma24projekat_19219.models.types.Zemljiste
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ZemljisteConverter {
    @TypeConverter
    fun fromZemljisteTipovi(value: Zemljiste?): String? {
        return value?.name
    }

    @TypeConverter
    fun toZemljiste(value: String?): Zemljiste? {
        return value?.let { Zemljiste.valueOf(it) }
    }
}

class ListZemljisteConverter {
    @TypeConverter
    fun fromZemljisteList(value: List<Zemljiste>?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Zemljiste>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toZemljisteList(value: String?): List<Zemljiste>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Zemljiste>>() {}.type
        return gson.fromJson(value, type)
    }
}

