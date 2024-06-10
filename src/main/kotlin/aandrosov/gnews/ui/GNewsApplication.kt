package aandrosov.gnews.ui

import aandrosov.gnews.data.database.GNewsDatabase
import android.app.Application

class GNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GNewsDatabase.initialize(applicationContext)
    }
}