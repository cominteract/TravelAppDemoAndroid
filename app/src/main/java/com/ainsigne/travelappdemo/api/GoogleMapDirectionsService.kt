package com.ainsigne.travelappdemo.api



import com.ainsigne.travelappdemo.data.GoogleDirectionsData
import com.ainsigne.travelappdemo.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * [GoogleMapDirectionsService] service for the api calls on the Foursquare API
 */
interface GoogleMapDirectionsService {


    @GET("json?key=${API_KEY}")
    suspend fun getDirection(@Query("origin") origin : String,@Query("destination") destination : String) : Response<GoogleDirectionsData>

}