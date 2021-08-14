package br.com.fomezero.centralfood.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import br.com.fomezero.centralfood.R
import br.com.fomezero.centralfood.presentation.login.LoginViewModel
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_CentralFood)
        setContentView(R.layout.activity_main)
    }
}