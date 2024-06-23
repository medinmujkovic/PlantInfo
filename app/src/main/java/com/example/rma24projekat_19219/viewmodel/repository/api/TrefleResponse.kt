package com.example.rma24projekat_19219.viewmodel.repository.api

import com.google.gson.annotations.SerializedName

data class TrefleResponse2(
    @SerializedName("data") val data: PlantData
)
data class TrefleResponse(
    @SerializedName("data") val data: List<PlantData>
)
data class PlantData(
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val common_name: String?,
    @SerializedName("scientific_name") val scientific_name: String,
    @SerializedName("family") val family: String?,
    @SerializedName("edible") val edible: Boolean?,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("specifications") val specifications: Specifications?,
    @SerializedName("growth") val growth: Growth?

)
data class Specifications(
    @SerializedName("toxicity") val toxicity: String?
)
data class Growth(
    @SerializedName("soil_texture") val soil_texture: List<String>?,
    @SerializedName("light") val light: Int?,
    @SerializedName("atmospheric_humidity") val atmospheric_humidity: Int?
)