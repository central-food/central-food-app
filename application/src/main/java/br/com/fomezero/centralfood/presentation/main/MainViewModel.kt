package br.com.fomezero.centralfood.presentation.main

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import br.com.fomezero.centralfood.domain.auth.Auth
import br.com.fomezero.centralfood.exceptions.UserNotFoundException
import br.com.fomezero.centralfood.model.UserType
import br.com.fomezero.centralfood.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val auth: Auth, val userRepository: UserRepository) : ViewModel() {
    fun initialAuthentication(view: View) {
        Log.d(TAG, "initialAuthentication(): Starting initial authentication")
        viewModelScope.launch {
            val user = auth.getCurrentUser()
            val action = if (user != null) {
                try {
                    val userData = userRepository.getUserData(user)
                    val action = when (userData.userType) {
                        UserType.MERCHANT -> {
                            Log.d(TAG, "initialAuthentication(): Merchant found")
                            MainFragmentDirections.actionToMerchantFragment()
                        }
                        UserType.NGO -> {
                            Log.d(TAG, "initialAuthentication(): NGO found")
                            MainFragmentDirections.actionToNgoFragment()
                        }
                    }
                    action
                } catch (e: UserNotFoundException) {
                    Log.e(TAG, "initialAuthentication: User data not found", e)
                    MainFragmentDirections.actionToLoginFragment()
                }
            } else {
                MainFragmentDirections.actionToLoginFragment()
            }
            withContext(Dispatchers.Main) {
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}