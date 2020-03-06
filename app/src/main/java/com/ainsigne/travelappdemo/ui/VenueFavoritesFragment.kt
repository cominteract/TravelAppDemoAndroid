package com.ainsigne.travelappdemo.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.ainsigne.travelappdemo.MainActivity
import com.ainsigne.travelappdemo.adapters.VenueFavesAdapter
import com.ainsigne.travelappdemo.data.VenueFavoritesRepository
import com.ainsigne.travelappdemo.data.VenueItemsRepository
import com.ainsigne.travelappdemo.databinding.FragmentVenueFavoritesBinding
import com.ainsigne.travelappdemo.utils.InjectorUtils
import com.ainsigne.travelappdemo.viewmodels.VenueFavoritesViewModel
import javax.inject.Inject
import javax.inject.Named

/**
 * A simple [Fragment] subclass.
 */
class VenueFavoritesFragment : Fragment() {


 @Inject lateinit var venueFavesViewModel : VenueFavoritesViewModel

//    private val venueFavesViewModel: VenueFavoritesViewModel by viewModels {
//        InjectorUtils.provideVenueFavoritesViewModelFactory(requireContext())
//    }

    //@field:[Inject Named("VenueFavesRepo")] lateinit var repo : VenueFavoritesRepository

    //@field:[Inject Named("FakeVenueFavesRepo")] lateinit var repo : FakeVenueFavoritesRepository


//    @field:[Inject Named("VenueFavorites")]
//    lateinit var venueFavesViewModel : VenueFavoritesViewModel





//    @field:[Inject Named("FakeVenueFavorites")]
//    lateinit var venueFavesViewModel : VenueFavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentVenueFavoritesBinding.inflate(inflater, container, false)
        (context as MainActivity).activityComponent.inject(this)
        // venueFavesViewModel = FactoryInjector().provideVenueFavoritesViewModelFactory().create(VenueFavoritesViewModel::class.java)
        val adapter = VenueFavesAdapter()
        binding.faveList.adapter = adapter
        subscribeUi(adapter, binding)
        return binding.root
    }
    private fun subscribeUi(adapter: VenueFavesAdapter, binding: FragmentVenueFavoritesBinding) {
        venueFavesViewModel.venueFavorites.observe(viewLifecycleOwner){ venueFaves ->
            binding.hasVisited = !venueFaves.isNullOrEmpty()
        }

        venueFavesViewModel.venueAndFavorites.observe(viewLifecycleOwner) { result ->
            if (!result.isNullOrEmpty())
                adapter.submitList(result)
        }
    }


}
