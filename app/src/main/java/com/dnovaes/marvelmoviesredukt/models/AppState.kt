package com.dnovaes.marvelmoviesredukt.models

data class AppState(
    val movies: Map<Int, Movie> = LinkedHashMap(),
    val syncRunning: Boolean = false,
    val searchResult: Map<Int, Movie>? = null,
    val stateLoaded: Boolean = false
) {

    fun filteredMovies(): Map<Int, Movie> {
        return searchResultOrderByScore() ?: moviesOrderedByScore()
    }

    private fun searchResultOrderByScore(): Map<Int, Movie>? {
        return if (searchResult.isNullOrEmpty())
            null
        else {
            searchResult.values.sortedByDescending { it.score }.map { it._id.toInt() to it}.toMap()
        }
    }

    private fun moviesOrderedByScore(): Map<Int, Movie> {
        return if (movies.isNullOrEmpty())
            linkedMapOf()
        else {
            movies.values.sortedByDescending { it.score }.map { it._id.toInt() to it}.toMap()
        }
    }
}

