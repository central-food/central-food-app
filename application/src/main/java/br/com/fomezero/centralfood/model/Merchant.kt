package br.com.fomezero.centralfood.model

data class Merchant(
    override val name: String,
    override val email: String,
    override val phoneNumber: String,
    override val imageUrl: String,
) : UserData {
    override val userType = UserType.MERCHANT
}