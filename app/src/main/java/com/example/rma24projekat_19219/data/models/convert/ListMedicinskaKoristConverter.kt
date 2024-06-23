package com.example.rma24projekat_19219.data.models.convert

import androidx.room.TypeConverter
import com.example.rma24projekat_19219.data.models.types.MedicinskaKorist
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MedicinskaKoristConverter {
    @TypeConverter
    fun fromMedicinskaKorist(value: MedicinskaKorist?): String? {
        return value?.name
    }

    @TypeConverter
    fun toMedicinskaKorist(value: String?): MedicinskaKorist? {
        return value?.let { MedicinskaKorist.valueOf(it) }
    }
}


class ListMedicinskaKoristConverter {
    @TypeConverter
    fun fromMedicinskaKoristList(value: List<MedicinskaKorist>?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMedicinskaKoristList(value: String?): List<MedicinskaKorist>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return gson.fromJson(value, type)
    }
}
