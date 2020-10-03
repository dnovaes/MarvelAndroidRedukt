package com.dnovaes.marvelmoviesredukt.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Movie(
    @Id(assignable = true) var id: Long = 1,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val actors: String,
    val plot: String,
    val poster: String
) {
}

