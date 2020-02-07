package com.ainsigne.travelappdemo.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.ainsigne.travelappdemo.MainActivity
import com.ainsigne.travelappdemo.R
import com.ainsigne.travelappdemo.adapters.VenueAdapter
import com.ainsigne.travelappdemo.api.FoursquareAPI
import com.ainsigne.travelappdemo.data.TravelLocations
import com.ainsigne.travelappdemo.data.VenueItemsRepository
import com.ainsigne.travelappdemo.databinding.FragmentVenueItemsBinding
import com.ainsigne.travelappdemo.fake.FakeVenueItemsRepository
import com.ainsigne.travelappdemo.utils.LocationUtils
import com.ainsigne.travelappdemo.viewmodels.VenueItemsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * [VenueItemsFragment] Fragment for displaying all the venues from the current location.
 */
class VenueItemsFragment : Fragment() {

    //@field:[Inject Named("VenueItems")] lateinit var viewModel: VenueItemsViewModel

    //@field:[Inject Named("FakeVenueItems")] lateinit var viewModel: VenueItemsViewModel

    @Inject lateinit var viewModel : VenueItemsViewModel

    @Inject lateinit var repo : VenueItemsRepository

    //@field:[Inject Named("VenueItemsRepo")] lateinit var repo : VenueItemsRepository

    //@field:[Inject Named("FakeVenueItemsRepo")] lateinit var repo : FakeVenueItemsRepository

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
        val adapter = VenueAdapter()
        binding.venueList.adapter = adapter
        subscribeUi(adapter)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
        viewModel.venueitems.observe(viewLifecycleOwner) { items ->
            Log.d(" Venue Size ${items.size}"," Venue Size ${items.size}")
            if (!items.isNullOrEmpty()) adapter.submitList(items)
        }

        viewModel.travelLocations.observe(viewLifecycleOwner){
            items ->
            if(items.isNullOrEmpty())
                startAPI()
            else if(LocationUtils.isAlreadyChecked(40.7243,-74.0018, items))
            {

            }
        }
    }

    private fun startAPI()
    {
        val lat = 40.7243
        val lon = -74.0018
        val travelLocations = TravelLocations()
        travelLocations.lat = lat
        travelLocations.lon = lon
        val locationQuery = LocationUtils.locationQuery(lat,lon)
        CoroutineScope(Dispatchers.IO).launch {
            val response = foursquare.webservice.getVenueItems(ll = locationQuery )
            if(response.isSuccessful)
            {
                response.body()?.response?.groups?.let { groups ->
                    if(groups.isNotEmpty()){
                        groups[0].items?.let { items ->
                            CoroutineScope(Dispatchers.IO).launch {
                                Log.d(" 2nd Venue Size ${items.size}"," 2nd Venue Size ${items.size}")
                                repo.insertAll(items.toList())
                                repo.insertTravelLocations(travelLocations)
                            }
                        }
                    }
                }
            }
        }
    }
}
