package com.ainsigne.travelappdemo.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.ainsigne.travelappdemo.MainActivity
import com.ainsigne.travelappdemo.R
import com.ainsigne.travelappdemo.api.FoursquareAPI
import com.ainsigne.travelappdemo.data.VenueDetailsRepository
import com.ainsigne.travelappdemo.data.VenueFavoritesRepository
import com.ainsigne.travelappdemo.databinding.FragmentVenueDetailsBinding
import com.ainsigne.travelappdemo.fake.FakeVenueDetailsRepository
import com.ainsigne.travelappdemo.fake.FakeVenueFavoritesRepository
import com.ainsigne.travelappdemo.viewmodels.VenueDetailsViewModel
import com.ainsigne.travelappdemo.viewmodels.VenueDetailsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_venue_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * [VenueDetaisFragment] Fragment for displaying details from selected venue.
 */
class VenueDetailsFragment : Fragment() {

    private val args: VenueDetailsFragmentArgs by navArgs()


    @Inject lateinit var repo : VenueDetailsRepository

    @Inject  lateinit var faveRepo : VenueFavoritesRepository

//    var repo = FakeVenueDetailsRepository()
//    var faveRepo = FakeVenueFavoritesRepository()


    //@field:[Inject Named("VenueDetailsRepo")] lateinit var repo : VenueDetailsRepository

    //@field:[Inject Named("FakeVenueDetailsRepo")] lateinit var repo : FakeVenueDetailsRepository

    //@field:[Inject Named("VenueFavesRepo")] lateinit var faveRepo : VenueFavoritesRepository

    //@field:[Inject Named("FakeVenueFavesRepo")] lateinit var faveRepo : FakeVenueFavoritesRepository




    private lateinit var detailsViewModel: VenueDetailsViewModel

    private lateinit var shareText: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentVenueDetailsBinding>(
            inflater, R.layout.fragment_venue_details, container, false).apply {
            (context as MainActivity).activityComponent.inject(this@VenueDetailsFragment)
            detailsViewModel = VenueDetailsViewModelFactory(repo,faveRepo,args.venueId).create(VenueDetailsViewModel::class.java)
//          detailsViewModel = FactoryInjector().provideVenueDetailsViewModelFactory(args.venueId).create(VenueDetailsViewModel::class.java)
            viewModel = detailsViewModel
            lifecycleOwner = this@VenueDetailsFragment
            fab.setOnClickListener { view ->
                detailsViewModel.addVenueToFave()
                Snackbar.make(view, R.string.added_venue_to_favorites, Snackbar.LENGTH_LONG).show()
            }

        }

        detailsViewModel.venueDetailWithId.observe(viewLifecycleOwner){venueDetail ->

            if(venueDetail == null) {
                shareText =  ""
                Log.d(" Starting the api"," Starting the api")
                startAPI()
            } else {
                shareText =  "Check out the ${venueDetail.name} in Andre\\'s TravelDemoApp"

                img_to_map.setOnClickListener {
                    venueDetail.latLng()?.let {latLng ->

                        val direction = VenueDetailsFragmentDirections.actionItemsFragmentToVenueLocationFragment(origin = args.origin , dest = latLng)
                        it.findNavController().navigate(direction)
                    }
                }

                //getString(R.string.share_text_venue, venueDetails.name)
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun startAPI()
    {
        val foursquare = FoursquareAPI()
        CoroutineScope(Dispatchers.IO).launch {
            val response = foursquare.webservice.getVenueDetails(args.venueId)
            if (response.isSuccessful) {

                response.body()?.response?.venue?.let {
                    Log.d(" Starting the api"," Starting the api result")
                    repo.insert(venueDetails = it)
                }
            }
            else Log.d(" Error ", " Error ${response.code()} ${response.raw()} ${args.venueId}")
        }
    }

}
