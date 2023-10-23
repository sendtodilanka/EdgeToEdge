package com.codeboxlk.tutorial.edgetoedge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeboxlk.tutorial.edgetoedge.data.DataStoreManager
import com.codeboxlk.tutorial.edgetoedge.data.DataStoreManager.Companion.darkModeKey
import com.codeboxlk.tutorial.edgetoedge.data.DataStoreManager.Companion.dynamicColorKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    val getDarkModeState: Flow<Boolean> = dataStoreManager.readValue(darkModeKey).filterNotNull()

    val getDynamicColorState: Flow<Boolean> = dataStoreManager.readValue(dynamicColorKey).filterNotNull()

    fun setDarkMode(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.storeValue(darkModeKey, state)
        }
    }

    fun setDynamicColor(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.storeValue(dynamicColorKey, state)
        }
    }
}