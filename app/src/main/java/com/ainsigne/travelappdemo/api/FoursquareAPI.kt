package com.ainsigne.travelappdemo.api

import com.ainsigne.travelappdemo.utils.BASE_API
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * [FoursquareAPI] declaration of its retrofit service builder for making http calls
 */
class FoursquareAPI{

    val webservice: FoursquareService by lazy {

//        val httpClient = OkHttpClient.Builder()
//        val client = httpClient.addInterceptor { chain ->
//            var request: Request = chain.request()
//            val url: HttpUrl =
//                request.url().newBuilder().
//                    addQueryParameter("client_id", CLIENT_ID).
//                    addQueryParameter("client_secret", CLIENT_SECRET).
//                    addQueryParameter("v", V).build()
//            request = request.newBuilder().url(url).build()
//            chain.proceed(request)
//        }.build()

        Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(FoursquareService::class.java)
    }


}
