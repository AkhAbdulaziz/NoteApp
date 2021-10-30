package uz.xsoft.noteapp.app

import android.app.Application
import timber.log.Timber
import uz.xsoft.noteapp.BuildConfig

class App : Application() {
    companion object {
        lateinit var instance : App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}