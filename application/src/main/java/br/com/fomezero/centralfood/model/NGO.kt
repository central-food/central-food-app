package br.com.fomezero.centralfood.model

data class NGO(
    override val name: String,
    override val email: String,
    override val imageUrl: String,
    override val phoneNumber: String,
    val documentNumber: String,
    val description: String,
    val isApproved: Boolean,
    val ownerName: String,
    val smsNotification: Boolean,
): UserData {
    override val userType = UserType.NGO
}