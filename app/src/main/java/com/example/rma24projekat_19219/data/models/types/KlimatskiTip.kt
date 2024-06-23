package com.example.rma24projekat_19219.data.models.types
enum class KlimatskiTip(val opis: String, val light: IntRange, val atmospheric_humidity: IntRange) {
    SREDOZEMNA("Mediteranska klima - suha, topla ljeta i blage zime",6..9, 1..5),
    TROPSKA( "Tropska klima - topla i vlažna tokom cijele godine",8..10, 7..10),
    SUBTROPSKA("Subtropska klima - blage zime i topla do vruća ljeta",6..9, 5..8),
    UMJERENA("Umjerena klima - topla ljeta i hladne zime",4..7, 3..7),
    SUHA("Sušna klima - niske padavine i visoke temperature tokom cijele godine",7..9, 1..2),
    PLANINSKA("Planinska klima - hladne temperature i kratke sezone rasta",0..5, 3..7, )
}