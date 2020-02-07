package com.ainsigne.travelappdemo.utils

import com.ainsigne.travelappdemo.data.TravelLocations

class LocationUtils {

    companion object{
        fun isAlreadyChecked(lat : Double, lon : Double, items : List<TravelLocations>) : Boolean{
            return !items.filter {
                (it.lat == lat && it.lon == lon) || ((it.lat > lat && it.lat - lat <= 1) &&
                        (it.lon > lon && it.lon - lon <= 1)) ||
                        ((it.lat < lat && lat - it.lat <= 1) && (it.lon < lon && lon - it.lon <= 1))}.isNullOrEmpty()
        }
        // (it.lat - lat) <= 1 &&  (it.lon - lon) <= 1 && (it.lon + lon) <= 1 && (it.lat + lat) <= 1

        fun locationQuery(lat : Double, lon : Double) : String{
            return "$lat,$lon"
        }
    }

}