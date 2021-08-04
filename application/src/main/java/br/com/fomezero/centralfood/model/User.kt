package br.com.fomezero.centralfood.model

import com.google.firebase.firestore.Exclude

data class User(
    val uid: String,
    var name: String?,
    var email: String?,

    @Exclude
    var isAuthenticated: Boolean = false,

    @Exclude
    var isNew: Boolean = false,

    @Exclude
    var isCreated: Boolean = false,
)