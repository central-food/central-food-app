package br.com.fomezero.centralfood.domain.auth

class AuthValidatorImpl: AuthValidator {
    override fun isValidEmail(email: String): Boolean {
        // TODO: Implement better email validation
        return email.contains('@')
    }

    override fun isValidPassword(password: String): Boolean {
        // TODO: Implement better password validation
        return password.length >= 6
    }
}