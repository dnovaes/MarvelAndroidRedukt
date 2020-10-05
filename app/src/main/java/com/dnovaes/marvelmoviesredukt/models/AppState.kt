package com.dnovaes.marvelmoviesredukt.models

data class AppState(
    val movies: Map<Int, Movie> = LinkedHashMap(),
    val syncRunning: Boolean = false,
    val searchResult: Map<Int, Movie>? = null,
    val stateLoaded: Boolean = false
) {

    fun getFilteredMovies(): Map<Int, Movie> = searchResult ?: movies
}

