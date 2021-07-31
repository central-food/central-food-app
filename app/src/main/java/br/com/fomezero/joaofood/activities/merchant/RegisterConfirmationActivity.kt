package br.com.fomezero.joaofood.activities.merchant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fomezero.joaofood.R
import kotlinx.android.synthetic.main.activity_registrer_confirmation.homeButton

class RegisterConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrer_confirmation)

        homeButton.setOnClickListener {
            val intentHome = Intent(this, MerchantHomeActivity::class.java)
            startActivity(intentHome)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intentHome = Intent(this, MerchantHomeActivity::class.java)
        startActivity(intentHome)
        finish()
    }
}