package br.com.fomezero.centralfood.presentation.login

import androidx.lifecycle.ViewModel
import br.com.fomezero.centralfood.domain.auth.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(val auth: Auth) : ViewModel() {
    fun signIn() {
        GlobalScope.launch(Dispatchers.IO) {
            val result = auth.signInWithEmailAndPassword("merchant@gmail.com", "123456")
        }
    }

}