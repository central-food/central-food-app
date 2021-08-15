package br.com.fomezero.centralfood.repository

import br.com.fomezero.centralfood.model.User
import br.com.fomezero.centralfood.model.UserData

interface UserRepository {
    suspend fun getUserData(user: User): UserData
}