package com.ainsigne.travelappdemo.di

import com.ainsigne.travelappdemo.MainActivity
import com.ainsigne.travelappdemo.api.FoursquareAPI
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val act: MainActivity) {
    @Provides
    fun providesActivity() : MainActivity = act

    @Provides
    fun providesFoursquare() : FoursquareAPI = FoursquareAPI()

}