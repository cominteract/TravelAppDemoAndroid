package com.ainsigne.travelappdemo.utils

import android.location.Location
import android.util.Log
import com.ainsigne.travelappdemo.data.TravelLocations
import com.google.android.gms.maps.model.LatLng

class LocationUtils {

    companion object{
        fun isAlreadyChecked(lat : Double, lon : Double, items : List<TravelLocations>) : Boolean{
            return !items.filter {
                (it.lat == lat && it.lon == lon) || ((it.lat > lat && it.lat - lat <= 1) &&
                        (it.lon > lon && it.lon - lon <= 1)) ||
                        ((it.lat < lat && lat - it.lat <= 1) && (it.lon < lon && lon - it.lon <= 1))}.isNullOrEmpty()
        }
        // (it.lat - lat) <= 1 &&  (it.lon - lon) <= 1 && (it.lon + lon) <= 1 && (it.lat + lat) <= 1


        fun alreadyCovered(origin : LatLng, destination : LatLng) : Boolean {
            val locationStart = Location("Start")
            locationStart.latitude = origin.latitude
            locationStart.longitude = origin.longitude
            val locationEnd = Location("End")
            locationEnd.latitude = destination.latitude
            locationEnd.longitude = destination.longitude
            Log.d(" Starting API "," Starting API ${locationStart.distanceTo(locationEnd)}")
            return locationStart.distanceTo(locationEnd) < 500
        }

        fun isClose(lat : Double, lon : Double, items : List<TravelLocations>) : Boolean{

            return !items.filter { alreadyCovered(LatLng(lat,lon), LatLng(it.lat,it.lon)) }.isNullOrEmpty()

        }

        fun locationQuery(lat : Double, lon : Double) : String{
            return "$lat,$lon"
        }
    }

}