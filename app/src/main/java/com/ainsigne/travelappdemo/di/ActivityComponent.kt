package com.ainsigne.travelappdemo.di

import com.ainsigne.travelappdemo.MainActivity
import com.ainsigne.travelappdemo.ui.VenueDetailsFragment
import com.ainsigne.travelappdemo.ui.VenueFavoritesFragment
import com.ainsigne.travelappdemo.ui.VenueItemsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityModule::class,DatabaseModule::class])
interface ActivityComponent{
    fun mainActivity(): MainActivity
    fun inject(venueItemsFragment: VenueItemsFragment)
    fun inject(venueDetailsFragment: VenueDetailsFragment)
    fun inject(venueFavoritesFragment: VenueFavoritesFragment)

}