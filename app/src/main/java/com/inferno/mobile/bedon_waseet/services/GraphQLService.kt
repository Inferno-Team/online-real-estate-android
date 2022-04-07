package com.inferno.mobile.bedon_waseet.services

import com.inferno.mobile.bedon_waseet.responses.RealEstateDetailsResponse
import com.inferno.mobile.bedon_waseet.responses.RealEstateResponse
import com.inferno.mobile.bedon_waseet.responses.SignResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface GraphQLService {
    @POST("/graphql")
    fun register(@Query("query") query: String): Call<SignResponse>

    @POST("/graphql")
    fun login(@Query("query") query: String): Call<SignResponse>

    @POST("/graphql")
    fun getAllRealEstate(@Query("query") query: String): Call<RealEstateResponse>
    @POST("/graphql")
    fun getRealEstateDetails(@Query("query") query: String): Call<RealEstateDetailsResponse>


}