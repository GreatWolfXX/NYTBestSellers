package com.gwolf.nytbestsellers.di

import android.content.Context
import androidx.room.Room
import com.gwolf.nytbestsellers.data.database.NYTBestSellersDatabase
import com.gwolf.nytbestsellers.data.repository.NYTBestSellersRepositoryImpl
import com.gwolf.nytbestsellers.data.repository.store.NYTBestSellersLocalStore
import com.gwolf.nytbestsellers.data.repository.store.NYTBestSellersRestStore
import com.gwolf.nytbestsellers.domain.repository.NYTBestSellersRepository
import com.gwolf.nytbestsellers.util.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NYTBestSellersDatabase {
        return Room.databaseBuilder(context, NYTBestSellersDatabase::class.java, DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideRepository(
        restStore: NYTBestSellersRestStore,
        localStore: NYTBestSellersLocalStore
    ): NYTBestSellersRepository = NYTBestSellersRepositoryImpl(restStore, localStore)
}