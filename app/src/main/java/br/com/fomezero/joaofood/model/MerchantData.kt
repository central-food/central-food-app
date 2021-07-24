package br.com.fomezero.joaofood.model

data class MerchantData(
    val name: String,
    val imageUrl: String,
    val phoneNumber: String,
    val location: String, // TODO: Change to lat long
)
