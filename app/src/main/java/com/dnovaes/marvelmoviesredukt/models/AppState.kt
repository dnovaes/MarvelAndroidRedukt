package com.dnovaes.marvelmoviesredukt.models

data class AppState(
    val movies: Map<String, Movie> = LinkedHashMap(),
    val syncRunning: Boolean = false
) {
}

