package com.dnovaes.marvelmoviesredukt.reducers

import com.dnovaes.marvelmoviesredukt.actions.ActionCreator
import com.dnovaes.marvelmoviesredukt.actions.Actions.LOAD_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.SAVE_SEARCHED_MOVIES
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.services.RouteConstants.SAGA
import com.dnovaes.marvelmoviesredukt.models.payloads.MoviesPayload
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class MoviesReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(LOAD_MOVIES)
    fun loadMovies(state: AppState, payload: MoviesPayload): AppState {
        return when(payload.sagaType) {
            SAGA -> saveSaga(state, payload.content)
            else -> state
        }
    }

    @Reduce(SAVE_SEARCHED_MOVIES)
    fun saveSearchMovies(state: AppState, payload: MoviesPayload): AppState {
        return when(payload.sagaType) {
            SAGA -> saveSagaSearchResult(state, payload.content)
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

    private fun saveSagaSearchResult(state: AppState, movies: List<Movie>): AppState {
        val moviesMap = mutableMapOf<Int, Movie>()
        movies.forEachIndexed { index, movie ->
            moviesMap[index] = movie
        }
        ActionCreator.instance.updateSync(false)
        return state.copy(searchResult= LinkedHashMap(moviesMap))
    }

}
