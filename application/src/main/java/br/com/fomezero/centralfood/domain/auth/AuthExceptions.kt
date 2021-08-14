package br.com.fomezero.centralfood.domain.auth

object AuthExceptions {
    class InvalidEmailException: Exception("Invalid password")
    class InvalidPasswordException: Exception("Invalid password")
}