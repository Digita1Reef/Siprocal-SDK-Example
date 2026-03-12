package com.siprocal.sdkexample.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

// Declaring/Creating the DataStore File for Application
private val Context.dataStore by preferencesDataStore(
    name = "PreferenceDataStore"
)

class PreferenceDataStoreHelper(context: Context) : IPreferenceDataStoreAPI {

    private val dataSource = context.dataStore

    override fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataSource.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences -> preferences[key] ?: defaultValue }

    override suspend fun <T> getFirstPreference(key: Preferences.Key<T>, defaultValue: T): T =
        dataSource.data.first()[key] ?: defaultValue

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataSource.edit { preferences ->
            preferences[key] = value
        }
    }

    // This Function removes the Key Value pair from the datastore, hereby removing it completely.
    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataSource.edit { preferences ->
            preferences.remove(key)
        }
    }

    override suspend fun clearAllPreferences() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }
}
