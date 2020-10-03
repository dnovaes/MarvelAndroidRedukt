package com.dnovaes.marvelmoviesredukt

import android.app.Application
import android.content.Context
import com.dnovaes.marvelmoviesredukt.database.ObjectBox
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.github.raulccabreu.redukt.Redukt

class MarvelMoviesApplication: Application() {

    companion object {
        lateinit var redukt: Redukt<AppState>

        fun initializeRedukt(context: Context, appState: AppState, isDebug: Boolean = false):
            Redukt<AppState> {
            val redukt = Redukt(appState, isDebug)

            addReducers(redukt)
            addMiddlewares(context, redukt)

            return redukt
        }

        private fun addReducers(redukt: Redukt<AppState>) { }

        private fun addMiddlewares(context: Context, redukt: Redukt<AppState>) { }
    }

    override fun onCreate() {
        super.onCreate()

        ObjectBox.build(this)
        initializeRedukt(applicationContext,
                 AppState(syncRunning= false),
                 BuildConfig.DEBUG).let {
            redukt = it
        }
    }

    override fun onTerminate() {
        redukt.stop()
        super.onTerminate()
    }
}
