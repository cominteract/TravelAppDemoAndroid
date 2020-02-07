package com.ainsigne.travelappdemo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.observe
import com.ainsigne.travelappdemo.fake.FakeVenueDetailsRepository
import com.ainsigne.travelappdemo.fake.FakeVenueFavoritesRepository
import com.ainsigne.travelappdemo.fake.FakeVenueItemsRepository
import com.ainsigne.travelappdemo.utils.*
import com.ainsigne.travelappdemo.viewmodels.VenueDetailsViewModel
import com.ainsigne.travelappdemo.viewmodels.VenueFavoritesViewModel
import com.ainsigne.travelappdemo.viewmodels.VenueItemsViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class VenueUnitTest {


    inline fun <reified T> lambdaMock(): T = mock(T::class.java)

    lateinit var repository: FakeVenueItemsRepository
    lateinit var detailsRepo : FakeVenueDetailsRepository
    lateinit var viewmodel: VenueItemsViewModel
    lateinit var detailsViewmodel: VenueDetailsViewModel
    lateinit var venueAndFavoritesRepository: FakeVenueFavoritesRepository
    lateinit var favesViewmodel: VenueFavoritesViewModel
    lateinit var detailId : String

    fun observeVenueChanges(lifecycle: Lifecycle, observer: (List<VenueItem>) -> Unit) {
        viewmodel.venueitems.observe( { lifecycle } ) {venueItems ->
            venueItems?.let(observer)
        }
    }

    fun observeVenueDetailChanges(lifecycle: Lifecycle, observer: (List<VenueDetails>) -> Unit) {
        detailsViewmodel.venueDetails.observe( { lifecycle } ) {venueDetails ->
            venueDetails.let(observer)
        }
    }

    fun observeVenueFavesChanges(lifecycle: Lifecycle, observer: (List<VenueFavorites>) -> Unit) {
        favesViewmodel.venueFavorites.observe( { lifecycle } ) { venueFaves ->
            venueFaves.let(observer)
        }
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        detailId = "4"
        repository = FakeVenueItemsRepository()
        detailsRepo = FakeVenueDetailsRepository()
        viewmodel = VenueItemsViewModel(repository)
        venueAndFavoritesRepository = FakeVenueFavoritesRepository()
        favesViewmodel = VenueFavoritesViewModel(venueAndFavoritesRepository)
        detailsViewmodel = VenueDetailsViewModel(detailsRepo, venueAndFavoritesRepository, detailId)
    }

    @Test
    fun testVenueDetails(){
        val detailObserver = lambdaMock<(List<VenueDetails>) -> Unit>()
        val faveObserver = lambdaMock<(List<VenueFavorites>) -> Unit>()
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        observeVenueDetailChanges(lifecycle,detailObserver)
        observeVenueFavesChanges(lifecycle,faveObserver)
        detailsRepo.getVenueDetail(detailId).value?.let {
            assertTrue(it.id == detailId)
        }


        val venueDetails = VenueDetails(id = VenueDetailID, name = VenueDetailName,
            description = VenueDetailDesc)
        venueDetails.fakeUrl = VenueDetailPic
        detailsRepo.insert(venueDetails)
        detailsRepo.getVenueDetail(VenueDetailID).value?.let {
            assertTrue(it.id == VenueDetailID)
        }
        detailsRepo.getVenueDetails().value?.let{
            verify(detailObserver).invoke(it)
        }

        venueAndFavoritesRepository.createVenueFavorites(detailId)
        venueAndFavoritesRepository.getVenueFavorites().value?.let {
            verify(faveObserver).invoke(it)
        }
    }

    @Test
    fun testVenueItems() {
        val observer = lambdaMock<(List<VenueItem>) -> Unit>()
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        observeVenueChanges(lifecycle,observer)


        val venue = Venue(id = VenueIDSList[8] , name = VenueNamesList[8], categories = null)
        venue.fakeUrl = VenueIconsList[8]
        val venueItem = VenueItem(venueItemId = VenueIDSList[8].toInt(),venue = venue)
        repository.insertVenueItem(venueItem)
        // verify that it is added and it is the same list that is being updated meaning the repository works!
        viewmodel.venueitems.value?.let {
            assert(it.size == 10)
            verify(observer).invoke(it)
        }


    }


}
