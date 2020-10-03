package com.dnovaes.marvelmoviesredukt.services

import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.services.base.BaseApi
import com.dnovaes.marvelmoviesredukt.services.base.SagaBaseService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SagaServiceApi : BaseApi() {

    override val serviceURL = "https://private-b34167-rvmarvel.apiary-mock.com/"

    override val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(serviceURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(SagaBaseService::class.java)

    fun getSaga(onSuccess: ((List<Movie>?) -> Unit), onFail: ((String) -> Unit)) {
        service.getSaga().enqueue(handleResponse { content, errorMessage ->
            errorMessage?.let {
                onFail.invoke(it)
            } ?: apply {
                onSuccess.invoke(content)
            }
        })
    }
}
