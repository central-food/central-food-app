package br.com.fomezero.joaofood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.activities.merchant.SignUpMerchantActivity
import br.com.fomezero.joaofood.activities.ong.SignUpOngActivity
import kotlinx.android.synthetic.main.activity_welcome_new_user.merchantButton
import kotlinx.android.synthetic.main.activity_welcome_new_user.ongButton

class WelcomeNewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_new_user)

        merchantButton.setOnClickListener {
            onClickMerchant()
        }
        ongButton.setOnClickListener {
            onClickOng()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    private fun onClickMerchant() {
        val signUpIntent = Intent(this, SignUpMerchantActivity::class.java)
        startActivity(signUpIntent)
        finish()
    }

    private fun onClickOng() {
        val signUpIntent = Intent(this, SignUpOngActivity::class.java)
        startActivity(signUpIntent)
        finish()
    }
}