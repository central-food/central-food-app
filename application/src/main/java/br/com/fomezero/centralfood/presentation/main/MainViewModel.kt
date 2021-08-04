package br.com.fomezero.centralfood.presentation.main

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import br.com.fomezero.centralfood.domain.auth.Auth

class MainViewModel(private val auth: Auth) : ViewModel() {
    fun initialAuthentication(view: View) {
        val action = if (auth.getCurrentUser()?.isAuthenticated == true) {
            MainFragmentDirections.actionToMerchantFragment()
        } else {
            MainFragmentDirections.actionToLoginFragment()
        }

        Navigation.findNavController(view).apply{
            navigate(action)
        }
    }
}