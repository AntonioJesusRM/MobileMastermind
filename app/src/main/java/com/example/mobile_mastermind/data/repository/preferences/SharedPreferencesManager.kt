package com.example.mobile_mastermind.data.repository.preferences

import android.content.SharedPreferences
import android.util.Log
import com.example.mobile_mastermind.ui.extension.TAG
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun <T : Any?> set(key: String, value: T) {
        setValue(key, value)
    }

    private fun setValue(key: String, value: Any?) {
        when (value) {
            is String -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value.toInt()) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value.toFloat()) }
            is Long -> edit { it.putLong(key, value.toLong()) }
            else -> {
                Log.e(TAG, "l> SharedPreferenceExtensions Unsupported Type: $value")
            }
        }
    }

    private fun edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.sharedPreferences.edit()
        operation(editor)
        editor.apply()
    }

    fun saveStringSharedPreferences(key: String, value: String) {
        set(key, value)
    }

    fun getStringSharedPreferences(key: String, defaultValue: String = ""): String {
        val test = sharedPreferences.getString(key, defaultValue) ?: ""
        return test
    }

    fun saveIntSharedPreferences(key: String, value: Int) {
        set(key, value)
    }

    fun getIntSharedPreferences(key: String, defaultValue: Int = 0): Int {
        val number = sharedPreferences.getInt(key, defaultValue)
        return number
    }

    fun clearAllPreferences() {
        edit { it.clear() }
    }
}