package br.com.fomezero.centralfood.presentation.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import br.com.fomezero.centralfood.domain.auth.Auth
import br.com.fomezero.centralfood.domain.auth.AuthExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val auth: Auth) : ViewModel(), LoginClickListener {

    val invalidEmailOrPassword = MutableLiveData<Boolean>()
    val emptyField = MutableLiveData<Boolean>()
    val unexpectedError = MutableLiveData<Boolean>()
    val loading = MutableLiveData(false)

    val loginSuccessAction = MutableLiveData<NavDirections>()

    override fun onClickLogin(email: String?, password: String?) {
        Log.d(TAG, "onClickLogin()")

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            emptyField.postValue(true)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val result = auth.signInWithEmailAndPassword(email, password)
            loading.postValue(false)

            if (result.isFailure) {
                val e = result.exceptionOrNull()
                Log.e(TAG, "onClickLogin()", e)
                if (isInvalidInputException(e)) {
                    invalidEmailOrPassword.postValue(true)
                    return@launch
                }
            }

            val user = result.getOrNull()
            if (user != null) {
                // TODO: Implement account type business logic
                loginSuccessAction.postValue(LoginFragmentDirections.actionLoginFragmentToMerchantFragment())
            } else {
                unexpectedError.postValue(true)
            }
        }
    }

    private fun isInvalidInputException(e: Throwable?): Boolean {
        return e is AuthExceptions.InvalidEmailException || e is AuthExceptions.InvalidPasswordException
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}