package com.codeboxlk.tutorial.edgetoedge.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.codeboxlk.tutorial.edgetoedge.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(context: Context) {

    companion object {
        val darkModeKey = booleanPreferencesKey(name = "dark_mode")
        val dynamicColorKey = booleanPreferencesKey(name = "dynamic_color")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        context.getString(R.string.app_name)
    )

    private val dataStore = context.dataStore

    fun <T> readValue(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.catch {
            if (it is IOException) emit(emptyPreferences()) else throw it
        }.map {
            it[key]
        }
    }

    suspend fun <T> storeValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { it[key] = value }
    }
}