package com.ainsigne.travelappdemo.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ainsigne.travelappdemo.R
import com.ainsigne.travelappdemo.api.GoogleMapDirectionsAPI
import com.ainsigne.travelappdemo.data.GoogleDirectionsData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VenueLocationFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    val googleDirectionsAPI = GoogleMapDirectionsAPI()
    private val args: VenueLocationFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_venue_location, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFrag =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFrag!!.getMapAsync(this)
        Log.d(" On View Created "," On View Created ")
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        CoroutineScope(Dispatchers.IO).launch {
//            val response = googleDirectionsAPI.webservice.getDirection(
//                "SM Megamall, 4F Cyberzone EDSA corner, Doña Julia Vargas Ave, Ortigas Center, Mandaluyong, 1555 Metro Manila",
//                "Ayala Malls The 30th, 30 Meralco Ave, Pasig, 1605 Metro Manila"
//            )

            val response = googleDirectionsAPI.webservice.getDirection(
                args.origin,
                args.dest
            )

            if(response.isSuccessful){
                Log.d(" Response "," Response Success ${response.body()} ${response.raw()}")
                response.body()?.let { data ->
                    GoogleDirectionsData.convertDataToPolylineWithMarker(data, mMap)

                }
            }else{
                Log.d(" Response "," Response Fail")
            }
        }
    }
}
