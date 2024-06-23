package com.example.rma24projekat_19219.data.models.types

typealias Zemljište = Zemljiste

enum class Zemljiste(val soil_texture: Int, val naziv: String){
    SLJUNKOVITO(9, "Šljunkovito zemljište"),
    KRECNJACKO(10, "Krečnjačko zemljište"),
    GLINENO(1, "Glineno zemljište"),
    PJESKOVITO(3, "Pjeskovito zemljište"),
    ILOVACA(5, "Ilovača"),
    CRNICA(7, "Crnica")

}
