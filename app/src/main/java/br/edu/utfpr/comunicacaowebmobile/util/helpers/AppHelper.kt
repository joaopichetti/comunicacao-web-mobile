package br.edu.utfpr.comunicacaowebmobile.util.helpers

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFS_FILENAME = "default"

class AppHelper(val context: Context) {

    private fun openSharedPrefs(): SharedPreferences? {
        return context.getSharedPreferences(SHARED_PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    fun getStringPref(name: String): String? {
        return openSharedPrefs()?.getString(name, null)
    }

    fun setStringPref(name: String, value: String) {
        openSharedPrefs()?.edit()?.putString(name, value)?.apply()
    }

}