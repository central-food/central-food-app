package br.com.fomezero.joaofood.model

import java.math.BigDecimal

data class Product(
    val name: String,
    val amount: String,
    val price: BigDecimal,
    val imageUrl: String?,
    val merchantData: MerchantData
)
