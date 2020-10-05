package com.dnovaes.marvelmoviesredukt.middleware

import com.dnovaes.marvelmoviesredukt.actions.ActionCreator
import com.dnovaes.marvelmoviesredukt.actions.Actions.LOAD_STATE
import com.dnovaes.marvelmoviesredukt.actions.Actions.SAVE_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.UPDATE_MOVIE_SCORE
import com.dnovaes.marvelmoviesredukt.database.ObjectBox
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.models.payloads.MoviesPayload
import com.dnovaes.marvelmoviesredukt.services.RouteConstants.SAGA
import com.github.raulccabreu.redukt.actions.Action
import com.github.raulccabreu.redukt.middlewares.AfterAction
import com.github.raulccabreu.redukt.middlewares.BaseAnnotatedMiddleware
import com.github.raulccabreu.redukt.middlewares.BeforeAction
import timber.log.Timber

class DatabaseMiddleware : BaseAnnotatedMiddleware<AppState>() {

    @BeforeAction(LOAD_STATE)
    fun loadState(state: AppState, action: Action<*>) {
        val movies = ObjectBox.getAll(Movie::class.java, 0, 100)
        if (movies.isEmpty()) {
            ActionCreator.instance.syncMovies(SAGA)
        } else {
            ActionCreator.instance.loadMovies(MoviesPayload(SAGA, movies))
        }
    }

    @BeforeAction(SAVE_MOVIES)
    fun saveMovies(state: AppState, action: Action<*>) {
        val payload = action.payload as? MoviesPayload ?: return
        ObjectBox.removeAll(Movie::class.java)
        ObjectBox.putAll(payload.content, Movie::class.java)
        Timber.v("Finished saving movies with total of ${ObjectBox.getAll(Movie::class.java, 0, 100).count()}")
    }

    @AfterAction(SAVE_MOVIES)
    fun loadSavedMovies(state: AppState, action: Action<*>) {
        ActionCreator.instance.loadState()
    }

    @AfterAction(UPDATE_MOVIE_SCORE)
    fun updateMovieScore(state: AppState, action: Action<*>) {
        val movie = action.payload as? Movie ?: return
        ObjectBox.put(movie, Movie::class.java)
    }

}
