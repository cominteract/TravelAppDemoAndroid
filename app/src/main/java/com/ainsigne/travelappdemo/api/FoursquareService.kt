package com.ainsigne.travelappdemo.api

import com.ainsigne.travelappdemo.data.VenueData
import com.ainsigne.travelappdemo.data.VenueExploreData
import com.ainsigne.travelappdemo.utils.CLIENT_ID
import com.ainsigne.travelappdemo.utils.CLIENT_SECRET
import com.ainsigne.travelappdemo.utils.V
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * [FoursquareService] service for the api calls on the Foursquare API
 */
interface FoursquareService {
    /**
     * gets the response to retrieve the venue items as [Response<VenueExploreData>]
     * @param ll as [String] the lat and lng of the user
     * @return Response<VenueExploreData>
     */
    @GET("venues/explore?client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&v=${V}")
    suspend fun getVenueItems(@Query("ll") ll : String) : Response<VenueExploreData>

    /**
     * gets the response to retrieve the venue details from id as String
     * @param id as [String] the id for the details to retrieve
     * @return Response<VenueExploreData>
     */
    @GET("venues/{Id}?venuePhotos=5&client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&v=${V}")
    suspend fun getVenueDetails(@Path("Id") id : String) : Response<VenueData>

}