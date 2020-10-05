package com.dnovaes.marvelmoviesredukt

import android.app.Application
import android.content.Context
import com.dnovaes.marvelmoviesredukt.actions.ActionCreator
import com.dnovaes.marvelmoviesredukt.database.ObjectBox
import com.dnovaes.marvelmoviesredukt.middleware.DatabaseMiddleware
import com.dnovaes.marvelmoviesredukt.middleware.MoviesMiddleware
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.reducers.AppStateReducer
import com.dnovaes.marvelmoviesredukt.reducers.MoviesReducer
import com.dnovaes.marvelmoviesredukt.reducers.SyncReducer
import com.github.raulccabreu.redukt.Redukt
import timber.log.Timber

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

        private fun addReducers(redukt: Redukt<AppState>) {
            redukt.reducers["sync"] = SyncReducer()
            redukt.reducers["movies"] = MoviesReducer()
            redukt.reducers["appstate"] = AppStateReducer()
        }

        private fun addMiddlewares(context: Context, redukt: Redukt<AppState>) {
            redukt.middlewares["movies"] = MoviesMiddleware()
            redukt.middlewares["database"] = DatabaseMiddleware()
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) startLogging()

        ObjectBox.build(this)
        initializeRedukt(applicationContext,
                 AppState(syncRunning= false),
                 BuildConfig.DEBUG).let {
            redukt = it
            ActionCreator.instance.loadState()
        }
    }

    private fun startLogging() = Timber.plant(Timber.DebugTree())

    override fun onTerminate() {
        redukt.stop()
        super.onTerminate()
    }
}
