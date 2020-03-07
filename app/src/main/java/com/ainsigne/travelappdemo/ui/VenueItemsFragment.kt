package com.ainsigne.travelappdemo.ui

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.ainsigne.travelappdemo.MainActivity
import com.ainsigne.travelappdemo.R
import com.ainsigne.travelappdemo.adapters.VenueAdapter
import com.ainsigne.travelappdemo.api.FoursquareAPI
import com.ainsigne.travelappdemo.api.GPSTracker
import com.ainsigne.travelappdemo.data.TravelLocations
import com.ainsigne.travelappdemo.data.VenueItemsRepository
import com.ainsigne.travelappdemo.databinding.FragmentVenueItemsBinding
import com.ainsigne.travelappdemo.fake.FakeVenueItemsRepository
import com.ainsigne.travelappdemo.utils.InjectorUtils
import com.ainsigne.travelappdemo.utils.LocationUtils
import com.ainsigne.travelappdemo.viewmodels.VenueItemsViewModel
import com.ainsigne.travelappdemo.viewmodels.VenueItemsViewModelFactory
import kotlinx.android.synthetic.main.fragment_venue_items.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * [VenueItemsFragment] Fragment for displaying all the venues from the current location.
 */
class VenueItemsFragment : Fragment(), LocationListener {

     @Inject lateinit var viewModel : VenueItemsViewModel

     @Inject lateinit var repo : VenueItemsRepository

//    var repo = FakeVenueItemsRepository()
//
//    private val viewModel : VenueItemsViewModel by viewModels {
//        InjectorUtils.provideVenueItemsViewModelFactory(requireContext())
//    }


    //@field:[Inject Named("VenueItems")] lateinit var viewModel: VenueItemsViewModel

    //@field:[Inject Named("FakeVenueItems")] lateinit var viewModel: VenueItemsViewModel




    //    private val venueFavesViewModel: VenueFavoritesViewModel by viewModels {
//        InjectorUtils.provideVenueFavoritesViewModelFactory(requireContext())
//    }

    //@field:[Inject Named("VenueItemsRepo")] lateinit var repo : VenueItemsRepository

    //@field:[Inject Named("FakeVenueItemsRepo")] lateinit var repo : FakeVenueItemsRepository




    private lateinit var gpsTracker : GPSTracker



    private var lat = 14.593652
    private var lon = 121.058283
    private val travelLocations = TravelLocations()
    lateinit var adapter : VenueAdapter
    @Inject
    lateinit var foursquare : FoursquareAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentVenueItemsBinding.inflate(inflater, container, false)
        context ?: return binding.root
        (context as MainActivity).activityComponent.inject(this)
        adapter = VenueAdapter()
        binding.venueList.adapter = adapter

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        lon = location.longitude
        activity?.runOnUiThread {
            subscribeUi(adapter)
        }
    }
    override fun onProviderDisabled(provider: String) {

    }
    override fun onProviderEnabled(provider: String) {

    }
    override fun onStatusChanged(
        provider: String,
        status: Int,
        extras: Bundle
    ) {
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    gpsTracker.generateLocation()
                    tv_cover.visibility = View.GONE

                } else {
                    tv_cover.visibility = View.VISIBLE
                }
                return
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.activity?.let {
            gpsTracker = GPSTracker(it, this, this)
            tv_cover.visibility = View.VISIBLE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_venue_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: VenueAdapter) {

        travelLocations.lat = lat
        travelLocations.lon = lon
        viewModel.venueItemsNearby(travelLocations).observe(viewLifecycleOwner) { items ->
            Log.d(" Venue Size ${items.size}"," Venue Size ${items.size}")
            if (!items.isNullOrEmpty()) adapter.submitList(items)
        }

        viewModel.travelLocations.observe(viewLifecycleOwner){
            items ->
            if(LocationUtils.isAlreadyChecked(lat,lon, items) && LocationUtils.isClose(lat,lon, items))
            {

            }
            else
            {
                startAPI()
            }
        }
    }

    private fun startAPI()
    {


        val locationQuery = LocationUtils.locationQuery(lat,lon)
        CoroutineScope(Dispatchers.IO).launch {
            val response = foursquare.webservice.getVenueItems(ll = locationQuery )
            if(response.isSuccessful)
            {

                response.body()?.response?.groups?.let { groups ->
                    if(groups.isNotEmpty()){
                        groups[0].items?.let { items ->
                            CoroutineScope(Dispatchers.IO).launch {
                                for(item in items){
                                    item.latlng = "${travelLocations.lat},${travelLocations.lon}"
                                }

                                repo.insertAll(items.toList())
                                repo.insertTravelLocations(travelLocations)
                            }
                        }
                    }
                }
            }else{
                Log.d(" Response is "," Response is ${response.isSuccessful} ${response.raw()}")
            }
        }
    }
}
