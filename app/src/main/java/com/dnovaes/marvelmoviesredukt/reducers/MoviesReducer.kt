package com.dnovaes.marvelmoviesredukt.reducers

import com.dnovaes.marvelmoviesredukt.actions.Actions.SAVE_MOVIES
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.services.RouteConstants.SAGA
import com.dnovaes.marvelmoviesredukt.models.payloads.SaveMoviePayload
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class MoviesReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(SAVE_MOVIES)
    fun saveMovies(state: AppState, payload: SaveMoviePayload): AppState {
        return when(payload.sagaType) {
            SAGA -> saveSaga(state, payload.content)
            else -> state
        }
    }

    private fun saveSaga(state: AppState, movies: List<Movie>): AppState {
        val moviesMap = mutableMapOf<Int, Movie>()
        movies.forEachIndexed { index, movie ->
           moviesMap[index] = movie
        }
        return state.copy(movies = LinkedHashMap(moviesMap))
    }
}
