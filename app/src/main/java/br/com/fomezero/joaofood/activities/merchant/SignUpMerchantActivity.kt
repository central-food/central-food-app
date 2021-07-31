package br.com.fomezero.joaofood.activities.merchant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.activities.LoginActivity
import br.com.fomezero.joaofood.activities.WelcomeNewUserActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up_merchant.completeNameField
import kotlinx.android.synthetic.main.activity_sign_up_merchant.emailField
import kotlinx.android.synthetic.main.activity_sign_up_merchant.passwordConfirmationField
import kotlinx.android.synthetic.main.activity_sign_up_merchant.passwordField
import kotlinx.android.synthetic.main.activity_sign_up_merchant.phoneNumberField
import kotlinx.android.synthetic.main.activity_sign_up_merchant.signUpButton
import kotlinx.android.synthetic.main.activity_sign_up_merchant.textFields

class SignUpMerchantActivity : AppCompatActivity(), View.OnClickListener {
    private val db: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_merchant)


        auth = Firebase.auth
        signUpButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val email = emailField.text.toString()
        val password = passwordField.text.toString()

        val fields = textFields.children

        for (field in fields) {
            if (field is TextInputEditText && field.text.toString().isEmpty()) {
                val msg = Toast.makeText(
                    applicationContext,
                    "Please fill in all fields.",
                    Toast.LENGTH_LONG
                )
                msg.setGravity(Gravity.CENTER, 0, 400)
                msg.show()
                return
            }
        }

        if (validatePassword().not()) {
            val msg =
                Toast.makeText(applicationContext, "Passwords are different.", Toast.LENGTH_LONG)
            msg.setGravity(Gravity.CENTER, 0, 400)
            msg.show()
            return
        }


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    runOnUiThread {
                        Toast.makeText(baseContext, "Successful Sign Up!.", Toast.LENGTH_SHORT).show()
                        val welcomeNewUserIntent = Intent(this, LoginActivity::class.java)
                        startActivity(welcomeNewUserIntent)
                        finish()

                    }
                    saveDataToFirestore()
                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    runOnUiThread {
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
//                    updateUI(null)
                }
            }

    }


    private fun saveDataToFirestore() {
        val ongData = hashMapOf(
            "name" to completeNameField.text.toString(),
            "email" to emailField.text.toString(),
            "phoneNumber" to phoneNumberField.text.toString(),
        )
        db.collection("merchants")
            .add(ongData)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val userData = hashMapOf(
                    "email" to emailField.text.toString(),
                    "type" to "merchant",
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


    private fun validatePassword(): Boolean =
        passwordField.text.toString() == passwordConfirmationField.text.toString()

    override fun onBackPressed() {
        super.onBackPressed()
        val welcomeNewUserIntent = Intent(this, WelcomeNewUserActivity::class.java)
        startActivity(welcomeNewUserIntent)
        finish()
    }

    companion object {
        private const val TAG = "SignUpMerchantActivity"
    }
}