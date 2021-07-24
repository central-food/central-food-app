package br.com.fomezero.joaofood.model

data class SignUpData(
    val completeName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val phoneNumber: String
)