package com.dnovaes.marvelmoviesredukt.models

data class AppState(
    val movies: Map<Int, Movie> = LinkedHashMap(),
    val syncRunning: Boolean = false
) {
}

