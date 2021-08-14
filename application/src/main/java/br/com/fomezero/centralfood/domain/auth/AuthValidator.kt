package br.com.fomezero.centralfood.domain.auth

interface AuthValidator {
    fun isValidEmail(email: String): Boolean
    fun isValidPassword(password: String): Boolean
}