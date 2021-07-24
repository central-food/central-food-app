package br.com.fomezero.joaofood.model

data class Account (
    val email: String,
    val passwordHash: String,
    val accountType: AccountType,
    val isApproved: Boolean?,
)