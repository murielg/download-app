package com.gonzoapps.downloadapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val DOWNLOAD_STATUS = "download_status_preferences"

data class DownloadPreferences(val id: Long, val url: String)

class DownloadStatusRepository private constructor(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = DOWNLOAD_STATUS)

    private object PreferencesKeys {
        val ACTIVE_DOWNLOAD_ID = preferencesKey<Long>("download_id")
        val ACTIVE_DOWNLOAD_URL = preferencesKey<String>("download_url")
    }

    val downloadStatusFlow: Flow<DownloadPreferences>  = dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preferences ->
                val id = preferences[PreferencesKeys.ACTIVE_DOWNLOAD_ID] ?: 0
                val url = preferences[PreferencesKeys.ACTIVE_DOWNLOAD_URL] ?: ""
                DownloadPreferences(id, url)
            }

    suspend fun storeActiveDownload(id: Long, url: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACTIVE_DOWNLOAD_ID] = id
            preferences[PreferencesKeys.ACTIVE_DOWNLOAD_URL] = url
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DownloadStatusRepository? = null

        fun getInstance(context: Context): DownloadStatusRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = DownloadStatusRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}