package br.com.fomezero.joaofood.activities.merchant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.fomezero.joaofood.R
import kotlinx.android.synthetic.main.activity_merchant_home.navigationView

class MerchantHomeActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merchant_home)
        loadFragment(MerchantHomeFragment())

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home ->  {
                    loadFragment(MerchantHomeFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.profile -> {
                    loadFragment(MerchantProfileFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.addFood -> {
                    val newProductIntent = Intent(this, NewProductActivity::class.java)
                    startActivity(newProductIntent)
                }
            }

            return@setOnItemSelectedListener false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}