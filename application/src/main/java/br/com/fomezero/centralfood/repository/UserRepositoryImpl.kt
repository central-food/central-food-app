package br.com.fomezero.centralfood.repository

import br.com.fomezero.centralfood.domain.auth.await
import br.com.fomezero.centralfood.exceptions.UserNotFoundException
import br.com.fomezero.centralfood.model.Merchant
import br.com.fomezero.centralfood.model.NGO
import br.com.fomezero.centralfood.model.User
import br.com.fomezero.centralfood.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    UserRepository {
    @ExperimentalCoroutinesApi
    override suspend fun getUserData(user: User): UserData = withContext(Dispatchers.IO) {
        val users = db.collection("users")
        val query = users.whereEqualTo("email", user.email)

        val result = query.get().await() ?: throw UserNotFoundException()
        if (result.isEmpty) throw UserNotFoundException()

        val document = result.first()
        val userData = document.getDocumentReference("data")?.get()?.await()
            ?: throw UserNotFoundException()

        return@withContext when (document.getString("type")) {
            "merchant" -> {
                Merchant(
                    name = userData.getString("name") ?: "",
                    email = userData.getString("email") ?: "",
                    phoneNumber = userData.getString("phoneNumber") ?: "",
                    imageUrl = userData.getString("imageUrl") ?: ""
                )
            }
            "ngo", "ong" -> {
                NGO(
                    name = userData.getString("name") ?: "",
                    email = userData.getString("email") ?: "",
                    imageUrl = userData.getString("imageUrl") ?: "",
                    phoneNumber = userData.getString("phoneNumber") ?: "",
                    documentNumber = userData.getString("documentNumber")
                        ?: userData.getString("cnpj") ?: "",
                    description = userData.getString("description") ?: "",
                    isApproved = userData.getBoolean("isApproved") ?: false,
                    ownerName = userData.getString("ownerName") ?: "",
                    smsNotification = userData.getBoolean("sms_notification") ?: false,
                )
            }
            else -> throw UserNotFoundException()
        }
    }
}