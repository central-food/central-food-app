package br.com.fomezero.joaofood.activities.merchant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fomezero.joaofood.R

class NewFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_food)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intentHome = Intent(this, MerchantHomeActivity::class.java)
        startActivity(intentHome)
        finish()
    }
}