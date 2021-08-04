package br.com.fomezero.joaofood.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.activities.merchant.MerchantHomeActivity
import br.com.fomezero.joaofood.activities.ong.OngMainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.emailField
import kotlinx.android.synthetic.main.activity_login.loginButton
import kotlinx.android.synthetic.main.activity_login.passwordField
import kotlinx.android.synthetic.main.activity_login.progressBar
import kotlinx.android.synthetic.main.activity_login.signUpButton


class LoginActivity : AppCompatActivity() {

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
            progressBar.visibility = View.VISIBLE
            Log.d(TAG, "onStart: currentUser is not null")
            onLoginSuccess()
        }
    }

    private fun onLogin() {
        val email = emailField.text.toString()
        val password = passwordField.text.toString()

        if (email.isEmpty() or password.isEmpty()) {
            val msg =
                Toast.makeText(applicationContext, "Please fill in all fields.", Toast.LENGTH_LONG)
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
                    if (user != null) {
                        onLoginSuccess()
                    }
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    runOnUiThread {
                        Toast.makeText(baseContext, "Incorrect email or password.", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

    }

    private fun onLoginSuccess() {
        progressBar.visibility = View.VISIBLE
        ActiveUserData.updateData(
            object : ActiveUserData.UserDataCallback {
                override fun onSuccess() {
                    val data = ActiveUserData.data
                    val document = ActiveUserData.userDocument
                    Log.d(TAG, "onLoginSuccess: ${data?.getString("name")}")
                    if (data != null && document != null) {
                        runOnUiThread {
                            val welcomeIndent = when (document.getString("type")) {
                                "ong" -> Intent(this@LoginActivity, OngMainActivity::class.java)
                                "merchant" -> Intent(this@LoginActivity, MerchantHomeActivity::class.java)

                                else -> null
                            }
                            progressBar.visibility = View.GONE
                            welcomeIndent?.let {
                                startActivity(it)
                                this@LoginActivity.finish()
                            }
                        }
                    }
                }
                override fun onError(throwable: Throwable) {
                    progressBar.visibility = View.GONE
                    onConnectionError(throwable)
                }
            }
        )
    }

    private fun onConnectionError(throwable: Throwable) {
        Log.w(TAG, "Error getting documents.", throwable)
        runOnUiThread {
            Toast.makeText(baseContext, "Erro de conexão. Tente mais tarde.", Toast.LENGTH_LONG)
                .show()
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}