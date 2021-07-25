package br.com.fomezero.joaofood.activities.ong

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.fomezero.joaofood.R
import kotlinx.android.synthetic.main.activity_ong_main.navigationView

class OngMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ong_main)
        loadFragment(OngHomeFragment())

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home ->  {
                    loadFragment(OngHomeFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.profile -> {
                    loadFragment(OngProfileFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.notification -> {
                    loadFragment(NotificationFragment())
                    return@setOnItemSelectedListener true
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