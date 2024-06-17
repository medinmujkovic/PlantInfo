package com.example.rma24projekat_19219.DAO

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream


class ConvertBitmap {

//    var bmp = BitmapFactory.decodeResource(getResources(), R.drawable.xyz)
//
//    var resizedBmp = Bitmap.createBitmap(bmp, 0, 0, yourwidth, yourheight)

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}