package com.dnovaes.marvelmoviesredukt.actions

import com.dnovaes.marvelmoviesredukt.MarvelMoviesApplication
import com.dnovaes.marvelmoviesredukt.actions.Actions.DOWNLOAD_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.SEARCH_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.SAVE_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.SAVE_SEARCHED_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.UPDATE_SYNC
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.models.payloads.SaveMoviesPayload
import com.github.raulccabreu.redukt.actions.Action

class ActionCreator private constructor() {

    private object Holder {
        val INSTANCE = ActionCreator()
    }

    companion object {
        val instance: ActionCreator by lazy { Holder.INSTANCE }
    }

    fun fetchMovies(contentType: String) {
        asyncDispatch(Action<Any>(DOWNLOAD_MOVIES, contentType))
    }

    fun saveMovies(sagaType: String, content: List<Movie>) {
        asyncDispatch(Action<Any>(SAVE_MOVIES, SaveMoviesPayload(sagaType, content)))
    }

    fun updateSync(status: Boolean) {
        asyncDispatch(Action<Any>(UPDATE_SYNC, status))
    }

    fun searchMovies(filter: String) {
        asyncDispatch(Action<Any>(SEARCH_MOVIES, filter))
    }

    fun saveSearchedMovies(payload: SaveMoviesPayload) {
        asyncDispatch(Action<Any>(SAVE_SEARCHED_MOVIES, payload))
    }

    private fun asyncDispatch(action: Action<*>) {
        MarvelMoviesApplication.redukt.dispatch(action, true)
    }

}
