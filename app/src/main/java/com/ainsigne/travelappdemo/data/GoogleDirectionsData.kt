package com.ainsigne.travelappdemo.data

import android.graphics.Color
import android.location.Location
import android.util.Log
import com.ainsigne.googlemapdirectionssample.api.GoogleMapPolylineDecode
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


data class GoogleDirectionsData(var status : String?
, var geocoded_waypoints : List<GeocodedWaypoints>?, var routes : List<Routes>?) {
    companion object{

        fun alreadyCovered(origin : LatLng, destination : LatLng) : Boolean {
            val locationStart = Location("Start")
            locationStart.latitude = origin.latitude
            locationStart.longitude = origin.longitude
            val locationEnd = Location("End")
            locationEnd.latitude = destination.latitude
            locationEnd.longitude = destination.longitude

            return locationStart.distanceTo(locationEnd) < 500
        }

        fun convertDataToPolyline(data : GoogleDirectionsData) : PolylineOptions?{
            data.routes?.let { routes ->
                for (route in routes){

                    route.legs?.let { legs ->
                        for (leg in legs){

                            leg.start_address?.let { address ->
                                Log.d(" Address "," Address $address")
                            }
                            leg.end_address?.let { address ->
                                Log.d(" Address "," Address $address")
                            }

                            leg.distance?.let {distance ->
                                Log.d(" Distance "," Distance ${distance.text}")
                            }

                            leg.steps?.let { steps ->
                                var routeList = ArrayList<LatLng>()
                                for((count, step) in steps.withIndex()){
                                    if(count == steps.size - 1){
                                        step.end_location?.let {location ->
                                            var latlng = LatLng(location.lat, location.lng)
                                            routeList.add(latlng)
                                        }
                                    }else{
                                        step.start_location?.let {
                                                location ->
                                            var latlng = LatLng(location.lat, location.lng)
                                            routeList.add(latlng)
                                        }
                                    }


                                    //routeList.addAll(GoogleMapPolylineDecode.decodePoly(step.polyline.points))
                                }
                                val rectLine =
                                    PolylineOptions().width(10f).color(
                                        Color.RED
                                    )

                                for (routeLine in routeList) {
                                    rectLine.add(routeLine)
                                }
                                return rectLine
                            }
                        }
                    }
                }

            }
            return null
        }
        fun convertDataToPolylineWithMarker(data : GoogleDirectionsData, map : GoogleMap){
            data.routes?.let { routes ->
                for (route in routes){

                    route.legs?.let { legs ->
                        for (leg in legs){

                            leg.start_address?.let { address ->
                                Log.d(" Address "," Address $address")
                            }
                            leg.end_address?.let { address ->
                                Log.d(" Address "," Address $address")
                            }

                            leg.distance?.let {distance ->
                                Log.d(" Distance "," Distance ${distance.text}")
                            }

                            leg.steps?.let { steps ->
                                var routeList = ArrayList<LatLng>()
                                CoroutineScope(Dispatchers.Main).launch{
                                    for((count, step) in steps.withIndex()){
                                        if(count == steps.size - 1){
                                            step.end_location?.let {location ->
                                                var latlng = LatLng(location.lat, location.lng)
                                                routeList.add(latlng)
                                                leg.end_address?.let {address ->
                                                    val marker = LatLng(location.lat,location.lng)
                                                    map.addMarker(MarkerOptions().position(marker).title(address))
                                                }

                                            }
                                        }else{
                                            step.start_location?.let {
                                                    location ->
                                                var latlng = LatLng(location.lat, location.lng)
                                                routeList.add(latlng)
                                                val marker = LatLng(location.lat,location.lng)
                                                if(count == 0){
                                                    leg.start_address?.let {address ->
                                                        map.addMarker(MarkerOptions().position(marker).title(address))
                                                    }
                                                }else{
                                                    map.addMarker(MarkerOptions().position(marker).title("${step.maneuver} ${step.duration?.text}"))
                                                }
                                            }
                                        }
                                    }
                                    val options =
                                        PolylineOptions().width(10f).color(
                                            Color.RED
                                        )

                                    for (routeLine in routeList) {
                                        options.add(routeLine)
                                    }
                                    map.addPolyline(options)
                                    if(!options.points.isNullOrEmpty()){
                                        leg.start_location?.let {start ->
                                            var startLatLng = LatLng(start.lat,start.lng)
                                            map.moveCamera(CameraUpdateFactory.newLatLng(startLatLng))
                                            map.animateCamera(
                                                CameraUpdateFactory.newLatLngZoom(
                                                    startLatLng, 16.0f
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        fun addMarkerToSteps(data : GoogleDirectionsData, map : GoogleMap){
            data.routes?.let { routes ->
                for (route in routes){

                    route.legs?.let { legs ->
                        for (leg in legs){

                            leg.start_address?.let { address ->
                                Log.d(" Address "," Address $address")
                            }
                            leg.end_address?.let { address ->
                                Log.d(" Address "," Address $address")
                            }

                            leg.distance?.let {distance ->
                                Log.d(" Distance "," Distance ${distance.text}")
                            }

                            leg.steps?.let { steps ->

                                for((count, step) in steps.withIndex()){
                                    if(count == steps.size - 1){
                                        step.end_location?.let {location ->
                                            val marker = LatLng(location.lat,location.lng)
                                            map.addMarker(MarkerOptions().position(marker).title("${step.maneuver} ${step.duration}"))
                                        }
                                    }else{
                                        step.start_location?.let {
                                                location ->
                                            val marker = LatLng(location.lat,location.lng)
                                            map.addMarker(MarkerOptions().position(marker).title("${step.maneuver} ${step.duration}"))
                                        }
                                    }
                                    //routeList.addAll(GoogleMapPolylineDecode.decodePoly(step.polyline.points))
                                }

                            }
                        }
                    }
                }

            }
        }
    }
}

data class Routes(var summary : String, var legs : List<Legs>?)

data class GeocodedWaypoints(var geocoder_status : String, var place_id : String)

data class Legs(var steps : List<Steps>?, var distance : Distance?,var duration : Distance?, var start_location : GoogleLocation?, var end_location : GoogleLocation?,
                var start_address : String?, var end_address : String?)

data class Distance(var text : String, var value : Int)

data class Steps(var travel_mode : String?, var maneuver : String?, var polyline: Polyline, var start_location : GoogleLocation?, var end_location : GoogleLocation?,  var distance : Distance?,var duration : Distance?)

data class Polyline(var points : String)

data class GoogleLocation(var lat : Double, var lng : Double)

