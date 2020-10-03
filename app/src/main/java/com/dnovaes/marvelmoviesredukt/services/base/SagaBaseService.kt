package com.dnovaes.marvelmoviesredukt.services.base

import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.services.RouteConstants.SAGA
import retrofit2.Call
import retrofit2.http.GET

interface SagaBaseService {
    @GET(SAGA)
    fun getSaga(): Call<List<Movie>>
}