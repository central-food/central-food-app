package br.com.fomezero.joaofood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.fomezero.joaofood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up_ong.emailField
import kotlinx.android.synthetic.main.activity_sign_up_ong.passwordConfirmationField
import kotlinx.android.synthetic.main.activity_sign_up_ong.passwordField
import kotlinx.android.synthetic.main.activity_sign_up_ong.signUpButton

class SignUpOngActivity : AppCompatActivity(), View.OnClickListener {
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
                        Toast.makeText(baseContext, "Conta criada com sucesso!.", Toast.LENGTH_SHORT).show()
                    }
                    val user = auth.currentUser
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

    private fun validatePassword(): Boolean = passwordField.text.toString() == passwordConfirmationField.text.toString()

    companion object {
        private const val TAG = "SignUpMerchantActivity"
    }
}