package br.com.fomezero.centralfood

import android.app.Application
import br.com.fomezero.centralfood.di.mainModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CentralFoodApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(applicationContext)

        startKoin {
            androidLogger()
            androidContext(this@CentralFoodApplication)

            modules(mainModule)
        }
    }
}