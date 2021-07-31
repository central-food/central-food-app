package br.com.fomezero.joaofood.activities.ong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.activities.LoginActivity
import br.com.fomezero.joaofood.activities.WelcomeNewUserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up_ong.cnpjField
import kotlinx.android.synthetic.main.activity_sign_up_ong.completeNameField
import kotlinx.android.synthetic.main.activity_sign_up_ong.emailField
import kotlinx.android.synthetic.main.activity_sign_up_ong.ongNameField
import kotlinx.android.synthetic.main.activity_sign_up_ong.passwordConfirmationField
import kotlinx.android.synthetic.main.activity_sign_up_ong.passwordField
import kotlinx.android.synthetic.main.activity_sign_up_ong.phoneNumberField
import kotlinx.android.synthetic.main.activity_sign_up_ong.signUpButton
import kotlinx.android.synthetic.main.activity_sign_up_ong.websiteField

class SignUpOngActivity : AppCompatActivity(), View.OnClickListener {
    private val db: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_ong)


        auth = Firebase.auth
        signUpButton.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val welcomeNewUserIntent = Intent(this, WelcomeNewUserActivity::class.java)
        startActivity(welcomeNewUserIntent)
        finish()
    }

    override fun onClick(v: View?) {
        val email = emailField.text.toString()
        val password = passwordField.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    runOnUiThread {
                        Toast.makeText(baseContext, "Successful Sign Up!.", Toast.LENGTH_SHORT).show()
                        val welcomeNewUserIntent = Intent(this, LoginActivity::class.java)
                        startActivity(welcomeNewUserIntent)
                        finish()
                    }
                    val user = auth.currentUser
                    saveDataToFirestore()
//                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    runOnUiThread {
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
//                    updateUI(null)
                }
            }

    }

    private fun saveDataToFirestore() {
        val ongData = hashMapOf(
            "ownerName" to completeNameField.text.toString(),
            "name" to ongNameField.text.toString(),
            "siteUrl" to websiteField.text.toString(),
            "email" to emailField.text.toString(),
            "cnpj" to cnpjField.text.toString(),
            "phoneNumber" to phoneNumberField.text.toString(),
            "isApproved" to false,
        )
        db.collection("ongs")
            .add(ongData)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val userData = hashMapOf(
                    "email" to emailField.text.toString(),
                    "type" to "ong",
                    "data" to documentReference
                )
                db.collection("users")
                    .add(userData)
                    .addOnSuccessListener { usersDocumentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${usersDocumentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun validatePassword(): Boolean = passwordField.text.toString() == passwordConfirmationField.text.toString()

    companion object {
        private const val TAG = "SignUpMerchantActivity"
    }
}