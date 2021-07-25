package br.com.fomezero.joaofood.activities.merchant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fomezero.joaofood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_new_food.donationSwitch
import kotlinx.android.synthetic.main.activity_new_food.priceField
import kotlinx.android.synthetic.main.activity_new_food.priceInputLayout
import kotlinx.android.synthetic.main.activity_new_food.productNameField
import kotlinx.android.synthetic.main.activity_new_food.quantityField
import kotlinx.android.synthetic.main.activity_new_food.submitProductButton


class NewProductActivity : AppCompatActivity() {
    private val db: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_food)
        donationSwitch.isChecked = true

        donationSwitch.setOnClickListener {
            if (donationSwitch.isChecked) {
                priceInputLayout.visibility = View.GONE
            } else {
                priceInputLayout.visibility = View.VISIBLE
            }
        }

        submitProductButton.setOnClickListener {
            onSubmit()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intentHome = Intent(this, MerchantHomeActivity::class.java)
        startActivity(intentHome)
        finish()
    }

    private fun onSubmit() {
        val price = if (donationSwitch.isChecked) {
            "0.0"
        } else {
            priceField.text.toString()
        }

        val users = db.collection("users")
        val query = users.whereEqualTo("email", auth.currentUser?.email)
        query.get()
            .addOnSuccessListener { result ->
                if (result.isEmpty.not()) {
                    val document = result.first()
                    val productData = hashMapOf(
                        "name" to productNameField.text.toString(),
                        "amount" to quantityField.text.toString(),
                        "price" to price,
                        "user" to document.reference,
                    )
                    db.collection("products").add(productData)
                        .addOnSuccessListener { usersDocumentReference ->
                            Log.d(
                                TAG,
                                "DocumentSnapshot added with ID: ${usersDocumentReference.id}"
                            )
                            runOnUiThread {
                                Toast.makeText(baseContext, "Produto Adicionado.", Toast.LENGTH_LONG).show()
                            }
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                            finish()
                        }
                }


            }

    }

    companion object {
        private const val TAG = "NewProductActivity"
    }
}