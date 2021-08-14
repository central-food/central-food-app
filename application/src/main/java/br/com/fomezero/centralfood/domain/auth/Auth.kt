package br.com.fomezero.centralfood.domain.auth

import br.com.fomezero.centralfood.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface Auth {
    fun getCurrentUser(): User?
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User>
}