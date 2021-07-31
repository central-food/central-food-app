package br.com.fomezero.joaofood.activities

import android.util.Log
import br.com.fomezero.joaofood.model.MerchantData
import br.com.fomezero.joaofood.model.OngData
import br.com.fomezero.joaofood.model.Product
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

object ActiveUserData {
    private const val TAG = "ActiveUserData"

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
    val client: OkHttpClient by lazy { OkHttpClient() }

    private const val NOTIFICATION_URL = "https://joao-food-admin.us-south.cf.appdomain.cloud/send-sms/"


    fun updateData(callback: UserDataCallback) {
        if (auth.currentUser == null) {
            callback.onError(Exception("Must be authenticated"))
            return
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

    fun getProductList(callback: ProductListCallBack) {
        if (userDocument == null || data == null) {
            callback.onError(Exception("Must be authenticated"))
            return
        }

        db.collection("products")
            .get()
            .addOnSuccessListener { products ->
                GlobalScope.launch {
                    if (products.isEmpty.not()) {
                        val productList = arrayListOf<Product>()
                        for (product in products) {
                            val urlList: List<String>? = product.get("image") as List<String>?
                            val user = product.getDocumentReference("user")?.get()?.let {
                                Tasks.await(it)
                            }
                            val userdata = user?.getDocumentReference("data")?.get()?.let {
                                Tasks.await(it)
                            }

                            val merchantData = MerchantData(
                                userdata?.getString("name") ?: "",
                                userdata?.getString("imageUrl"),
                                userdata?.getString("phoneNumber") ?: "",
                            )
                            productList.add(
                                Product(
                                    product.getString("name") ?: "",
                                    product.getString("amount") ?: "",
                                    product.getString("price") ?: "0",
                                    urlList?.first(),
                                    merchantData
                                )
                            )
                        }
                        Log.i(TAG, "getProductList: onSuccess")
                        callback.onSuccess(productList)
                    }
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "getProductList: ", it)
                callback.onError(it)
            }
    }

    fun getOngList(callback: OngListCallback) {
        db.collection("ongs").get()
            .addOnSuccessListener { ongs ->
                val ongList = ArrayList<OngData>()
                for (document in ongs) {
                    val ong = OngData(
                        document.getString("name") ?: "",
                        document.getString("phoneNumber") ?: "",
                        document.getString("imageUrl"),
                        document.getString("description"),
                    )

                    ongList.add(ong)
                }

                callback.onSuccess(ongList)
            }.addOnFailureListener {
                Log.e(TAG, "getOngList: ", it)
                callback.onError(it)
            }
    }

    fun setNotifications(value: Boolean) {
        data?.reference?.set(hashMapOf("sms_notification" to value), SetOptions.merge())
    }

    fun getNotificationValue(): Boolean {
        return data?.getBoolean("sms_notification") ?: false
    }

    fun sendNotification(documentId: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val request: Request = Request.Builder()
                .url(NOTIFICATION_URL + documentId)
                .build()

            client.newCall(request).execute()
        }
    }

    fun signOut() {
        auth.signOut()
        userDocument = null
        data = null
    }

    interface OngListCallback {
        fun onSuccess(ongList: ArrayList<OngData>)
        fun onError(throwable: Throwable)
    }

    interface ProductListCallBack {
        fun onSuccess(productList: ArrayList<Product>)
        fun onError(throwable: Throwable)
    }

    interface UserDataCallback {
        fun onSuccess()
        fun onError(throwable: Throwable)
    }
}