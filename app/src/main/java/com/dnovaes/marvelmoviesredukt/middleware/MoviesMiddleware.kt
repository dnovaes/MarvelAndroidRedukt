package com.dnovaes.marvelmoviesredukt.middleware

import com.dnovaes.marvelmoviesredukt.actions.ActionCreator
import com.dnovaes.marvelmoviesredukt.actions.Actions.LOAD_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.SYNC_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.SEARCH_MOVIES
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.models.payloads.MoviesPayload
import com.dnovaes.marvelmoviesredukt.services.RouteConstants.SAGA
import com.dnovaes.marvelmoviesredukt.services.SagaServiceApi
import com.github.raulccabreu.redukt.actions.Action
import com.github.raulccabreu.redukt.middlewares.AfterAction
import com.github.raulccabreu.redukt.middlewares.BaseAnnotatedMiddleware
import com.github.raulccabreu.redukt.middlewares.BeforeAction
import timber.log.Timber

class MoviesMiddleware : BaseAnnotatedMiddleware<AppState>() {

    @BeforeAction(SYNC_MOVIES)
    fun syncMovies(state: AppState, action: Action<*>) {
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
            Timber.v("Download request for '$sagaType' content has failed: $errorMessage")
            onFinishRequest.invoke()
        })
    }

    @BeforeAction(SEARCH_MOVIES)
    fun searchMovies(state: AppState, action: Action<*>) {
        ActionCreator.instance.updateSync(true)
        val filter = action.payload as String
        val filteredMovies = state.movies
            .filter { it.value.title.contains(filter, true) }
            .map { it.value }.toList()
        ActionCreator.instance.saveSearchedMovies(MoviesPayload(SAGA, filteredMovies))
    }

    @AfterAction(LOAD_MOVIES)
    fun moviesLoaded(state: AppState, action: Action<*>) {
        ActionCreator.instance.stateLoaded()
    }
}
