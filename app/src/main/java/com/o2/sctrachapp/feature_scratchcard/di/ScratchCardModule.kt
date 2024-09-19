package com.o2.sctrachapp.feature_scratchcard.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.o2.sctrachapp.feature_scratchcard.data.local.CardDataStore
import com.o2.sctrachapp.feature_scratchcard.data.remote.ScratchCardApi
import com.o2.sctrachapp.feature_scratchcard.data.repository.ScratchCardRepositoryImpl
import com.o2.sctrachapp.feature_scratchcard.domain.repository.ScratchCardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScratchCardModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "scratch_card")

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideScratchCardRepository(
        api: ScratchCardApi,
        dataStore: CardDataStore
    ): ScratchCardRepository {
        return ScratchCardRepositoryImpl(api, dataStore)
    }

    @Provides
    @Singleton
    fun provideScratchCardApi(): ScratchCardApi {
        return Retrofit.Builder()
            .baseUrl(ScratchCardApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScratchCardApi::class.java)
    }
}