package br.com.fomezero.joaofood.model

data class OngData(
    val name: String,
    val phoneNumber: String,
    val imageUrl: String? = null,

    // TODO: Lat log
    val location: String? = null,
)