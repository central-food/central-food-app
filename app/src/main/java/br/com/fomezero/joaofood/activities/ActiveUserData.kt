package br.com.fomezero.joaofood.activities
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

object ActiveUserData {
    var userDocument: DocumentSnapshot? = null
        private set
    var data: DocumentSnapshot? = null
        private set

    private val db: FirebaseFirestore by lazy {
        Firebase.firestore
    }
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }


    fun updateData(callback: UserDataCallback) {
        if (auth.currentUser == null) {
            callback.onError(Exception("Must be authenticated"))
        }

        val users = db.collection("users")
        val query = users.whereEqualTo("email", auth.currentUser!!.email)
        query.get()
            .addOnSuccessListener { result ->
                if (result.isEmpty.not()) {
                    val document = result.first()
                    userDocument = document
                    val dataQuery = document.getDocumentReference("data")
                    dataQuery?.get()?.addOnSuccessListener { data ->
                        this.data = data
                        callback.onSuccess()
                    }?.addOnFailureListener { exception ->
                        callback.onError(Exception("Data not found", exception))
                    }
                } else {
                    callback.onError(Exception("User not found"))
                }
            }
            .addOnFailureListener { exception ->
                callback.onError(Exception("User not found", exception))
            }
    }

    interface UserDataCallback {
        fun onSuccess()
        fun onError(throwable: Throwable)
    }
}