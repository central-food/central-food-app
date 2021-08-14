package br.com.fomezero.centralfood.domain.auth

import android.util.Log
import br.com.fomezero.centralfood.model.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthImpl(
    private val auth: FirebaseAuth = Firebase.auth,
    private val validator: AuthValidator = AuthValidatorImpl()
): Auth {
    override fun getCurrentUser(): User? {
        return auth.currentUser?.toUser()
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User> =
        withContext(Dispatchers.IO) {
            // TODO: consider to use errors enum instead of exception
            if (validator.isValidEmail(email).not()) {
                return@withContext Result.failure(AuthExceptions.InvalidEmailException())
            }

            if (validator.isValidPassword(password).not()) {
                return@withContext Result.failure(AuthExceptions.InvalidPasswordException())
            }

            return@withContext firebaseSignInWithEmailAndPassword(email, password)
        }

    private suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): Result<User> =
        withContext(Dispatchers.IO) {
            try {
                val task = auth.signInWithEmailAndPassword(email, password).await()
                val user = task?.user
                return@withContext user?.let {
                    Result.success(user.toUser())
                } ?: Result.failure(Exception())
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "firebaseSignInWithEmailAndPassword:", e)
                return@withContext Result.failure(e)
            }

        }

    private fun FirebaseUser.toUser(): User {
        return User(this.uid, this.displayName, this.email)
    }

    companion object {
        const val TAG = "AuthFirebaseImpl"
    }
}