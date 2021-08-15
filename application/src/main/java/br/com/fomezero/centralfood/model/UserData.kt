package br.com.fomezero.centralfood.model

interface UserData {
    val name: String
    val email: String
    val imageUrl: String
    val phoneNumber: String
    val userType: UserType
}