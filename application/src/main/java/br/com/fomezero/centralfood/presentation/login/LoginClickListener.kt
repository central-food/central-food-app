package br.com.fomezero.centralfood.presentation.login

interface LoginClickListener {
    fun onClickLogin(email: String?, password: String?)
}