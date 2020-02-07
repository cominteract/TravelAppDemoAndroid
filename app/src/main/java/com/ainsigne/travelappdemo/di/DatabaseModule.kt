package com.ainsigne.travelappdemo.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ainsigne.travelappdemo.data.*
import com.ainsigne.travelappdemo.fake.FakeVenueDetailsRepository
import com.ainsigne.travelappdemo.fake.FakeVenueFavoritesRepository
import com.ainsigne.travelappdemo.fake.FakeVenueItemsRepository
import com.ainsigne.travelappdemo.interfaces.FaveRepository
import com.ainsigne.travelappdemo.utils.DATABASE_NAME
import com.ainsigne.travelappdemo.viewmodels.*
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class DatabaseModule(appContext: Context) {

    var appDatabase : AppDatabase


    init {
        appDatabase = Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            })
            .build()
    }

    @Provides
    @Singleton
    fun providesRoom() : AppDatabase {
        return appDatabase
    }

    @Provides
    @Singleton
    fun providesVenueItemDao() : VenueItemDao {
        return appDatabase.venueItemDao()
    }

    @Provides
    @Singleton
    fun providesVenueDetailsDao() : VenueDetailsDao {
        return appDatabase.venueDetailsDao()
    }

    @Provides
    @Singleton
    fun providesVenueFavoritesDao() : VenueFavoritesDao {
        return appDatabase.venueFavoritesDao()
    }



    @Provides
    @Singleton
    fun providesVenueFaveRepository(dao: VenueFavoritesDao) : VenueFavoritesRepository{
        return VenueFavoritesRepository(dao)

    }




    @Provides
    @Singleton
    fun providesVenueDetailsRepository(dao: VenueDetailsDao) : VenueDetailsRepository{
        return VenueDetailsRepository(dao)
    }




    @Provides
    @Singleton
    fun providesVenueItemRepository(dao: VenueItemDao) : VenueItemsRepository{
        val repo = VenueItemsRepository(dao)
        return repo
    }





    @Provides
    @Singleton
    fun provideVenueItemFactory(venueItemsRepository: VenueItemsRepository) : VenueItemsViewModelFactory{
        return VenueItemsViewModelFactory(venueItemsRepository)
    }


    @Provides
    @Singleton
    fun providesVenueFavoritesFactory(venueFaveRepository: VenueFavoritesRepository) : VenueFavoritesViewModelFactory{
        return VenueFavoritesViewModelFactory(venueFaveRepository)
    }


    @Provides
    @Singleton
    fun providesVenueFavoritesViewModel(factory: VenueFavoritesViewModelFactory) : VenueFavoritesViewModel {
        val viewModel = factory.create(VenueFavoritesViewModel::class.java)
        return  viewModel
    }




    @Provides
    @Singleton
    fun providesVenueItemViewModel(factory: VenueItemsViewModelFactory) : VenueItemsViewModel{
        val viewModel = factory.create(VenueItemsViewModel::class.java)
        return  viewModel
    }




}