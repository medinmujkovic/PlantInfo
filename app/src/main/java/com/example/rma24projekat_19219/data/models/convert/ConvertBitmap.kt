package com.example.rma24projekat_19219.data.models.convert

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.Base64

class ConvertBitmap {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.getEncoder().encodeToString(byteArray)
    }

    @TypeConverter
    fun toBitmap(base64String: String): Bitmap {
        val byteArray = Base64.getDecoder().decode(base64String)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}