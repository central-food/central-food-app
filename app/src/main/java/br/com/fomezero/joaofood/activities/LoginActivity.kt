package br.com.fomezero.joaofood.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.activities.merchant.MerchantHomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.emailField
import kotlinx.android.synthetic.main.activity_login.loginButton
import kotlinx.android.synthetic.main.activity_login.passwordField
import kotlinx.android.synthetic.main.activity_login.signUpButton
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private val db: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        loginButton.setOnClickListener {
            onLogin()
        }

        signUpButton.setOnClickListener {
            val signUpIntent = Intent(this, WelcomeNewUserActivity::class.java)
            startActivity(signUpIntent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d(TAG, "onStart: currentUser is not null")
            onLoginSuccess(currentUser)
        }
    }

    private fun onLogin() {
        val email = emailField.text.toString()
        val password = passwordField.text.toString()

        if (email.isEmpty() or password.isEmpty()) {
            val msg = Toast.makeText(applicationContext, "Preencha todos os campos.", Toast.LENGTH_LONG)
            msg.setGravity(Gravity.CENTER, 0, -400)
            msg.show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    runOnUiThread {
                        Toast.makeText(baseContext, "Email ou senha incorretos.", Toast.LENGTH_LONG).show()
                    }
                }
            }

    }

    private fun onLoginSuccess(user: FirebaseUser) {
        val users = db.collection("users")
        val query = users.whereEqualTo("email", user.email)
        query.get()
            .addOnSuccessListener { result ->
                if (result.isEmpty.not()) {
                    val document = result.first()
                    val dataQuery = document.getDocumentReference("data")
                    dataQuery?.get()?.addOnSuccessListener { data ->
                        Log.d(TAG, "onLoginSuccess: ${data.getString("name")}")
                        val welcomeIndent = when (document.getString("type")) {
                            "ong" -> Intent(this, MerchantHomeActivity::class.java)

                            "merchant" -> Intent(this, MerchantHomeActivity::class.java)

                            else -> null
                        }

                        welcomeIndent?.let {
                            startActivity(it)
                            finish()
                        }

                    }?.addOnFailureListener { exception ->
                        onConnectionError(exception)
                    }
                }
            }
            .addOnFailureListener { exception ->
                onConnectionError(exception)
            }
    }

    private fun onConnectionError(exception: Exception) {
        Log.w(TAG, "Error getting documents.", exception)
        runOnUiThread {
            Toast.makeText(baseContext, "Erro de conexão. Tente mais tarde.", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}