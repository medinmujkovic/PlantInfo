package com.example.rma24projekat_19219.viewmodel.convertors

import androidx.room.TypeConverter
import com.example.rma24projekat_19219.models.types.KlimatskiTip
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class KlimatskiTipConverter {
    @TypeConverter
    fun fromKlimatskiTip(value: KlimatskiTip?): String? {
        return value?.name
    }

    @TypeConverter
    fun toKlimatskiTip(value: String?): KlimatskiTip? {
        return value?.let { KlimatskiTip.valueOf(it) }
    }
}

class ListKlimatskiTipConverter {
    @TypeConverter
    fun fromKlimatskiTipList(value: List<KlimatskiTip>?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<KlimatskiTip>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toKlimatskiTipList(value: String?): List<KlimatskiTip>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<KlimatskiTip>>() {}.type
        return gson.fromJson(value, type)
    }
}
