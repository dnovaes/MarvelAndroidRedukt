package com.dnovaes.marvelmoviesredukt.actions

import com.dnovaes.marvelmoviesredukt.MarvelMoviesApplication
import com.dnovaes.marvelmoviesredukt.actions.Actions.DOWNLOAD_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.SAVE_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.UPDATE_SYNC
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.models.payloads.SaveMoviePayload
import com.github.raulccabreu.redukt.actions.Action

class ActionCreator private constructor() {

    private object Holder {
        val INSTANCE = ActionCreator()
    }

    private fun getState() = MarvelMoviesApplication.redukt.state

    companion object {
        val instance: ActionCreator by lazy { Holder.INSTANCE }
    }

    fun fetchMovies(contentType: String) {
        asyncDispatch(Action<Any>(DOWNLOAD_MOVIES, contentType))
    }

    fun saveMovies(sagaType: String, content: List<Movie>) {
        asyncDispatch(Action<Any>(SAVE_MOVIES, SaveMoviePayload(sagaType, content)))
    }

    fun updateSync(status: Boolean) {
        asyncDispatch(Action<Any>(UPDATE_SYNC, status))
    }

    private fun asyncDispatch(action: Action<*>) {
        MarvelMoviesApplication.redukt.dispatch(action, true)
    }

}
