package com.example.rma24projekat_19219.data.api

data class TrefleResponse2(
    val data: PlantData
)
data class TrefleResponse(
    val data: List<PlantData>
)
data class PlantData(
    val id: Int,
    val common_name: String?,
    val scientific_name: String,
    val family: String?,
    val edible: Boolean,
    val image_url: String?,
    val specifications: Specifications?,
    val growth: Growth?
)
data class Specifications(
    val toxicity: String?,
)
data class Growth(
    val soil_texture: List<String>?,
    val light: Int?,
    val atmospheric_humidity: Int?
)