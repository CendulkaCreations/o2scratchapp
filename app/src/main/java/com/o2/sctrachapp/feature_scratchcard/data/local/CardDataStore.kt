package com.o2.sctrachapp.feature_scratchcard.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val scratchCodeFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[SCRATCH_CODE]
        }

    suspend fun saveScratchCode(value: String?) {
        dataStore.edit { preferences ->
            value?.let {
                preferences[SCRATCH_CODE] = it
            } ?: preferences.remove(SCRATCH_CODE)
        }
    }

    val isActivatedFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[IS_ACTIVATED] ?: false
        }

    suspend fun setIsActivated(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_ACTIVATED] = value
        }
    }

    private companion object {
        val SCRATCH_CODE = stringPreferencesKey("scratch_code")
        val IS_ACTIVATED = booleanPreferencesKey("is_activated")
    }
}