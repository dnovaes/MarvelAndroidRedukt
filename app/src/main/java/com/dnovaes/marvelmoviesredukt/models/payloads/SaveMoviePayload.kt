package com.dnovaes.marvelmoviesredukt.models.payloads

import com.dnovaes.marvelmoviesredukt.models.Movie

data class SaveMoviePayload (
    val sagaType: String,
    val content: List<Movie>
)