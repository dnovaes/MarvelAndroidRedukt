package com.dnovaes.marvelmoviesredukt.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Movie(
    var id: Int = 0,
    @Id(assignable = true) var _id: Long = id.toLong(),
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val poster: String,
    val score: Double = 0.0
)

