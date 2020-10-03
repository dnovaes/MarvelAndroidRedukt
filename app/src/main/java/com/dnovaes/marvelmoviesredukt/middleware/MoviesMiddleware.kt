package com.dnovaes.marvelmoviesredukt.middleware

import com.dnovaes.marvelmoviesredukt.actions.ActionCreator
import com.dnovaes.marvelmoviesredukt.actions.Actions.DOWNLOAD_MOVIES
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.services.SagaServiceApi
import com.github.raulccabreu.redukt.actions.Action
import com.github.raulccabreu.redukt.middlewares.BaseAnnotatedMiddleware
import com.github.raulccabreu.redukt.middlewares.BeforeAction

class MoviesMiddleware : BaseAnnotatedMiddleware<AppState>() {

    @BeforeAction(DOWNLOAD_MOVIES)
    fun fetchMarvelSaga(state: AppState, action: Action<*>) {
        val sagaType = action.payload as String
        ActionCreator.instance.updateSync(true)

        //This could be any type of saga: Marvel, Dbz, GameOfThrones... reflecting API route name
        sendDownloadRequest(sagaType) {
            ActionCreator.instance.updateSync(false)
        }
    }

    private fun sendDownloadRequest(sagaType: String, onFinishRequest: () -> Unit) {
        SagaServiceApi.getSaga ({ movies ->
            movies?.let { ActionCreator.instance.saveMovies(sagaType, it) }
            onFinishRequest.invoke()
        }, { errorMessage ->
            println("logd Error when fetching '$sagaType': $errorMessage")
            onFinishRequest.invoke()
        })
    }
}
