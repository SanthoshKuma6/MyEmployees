package com.management.myemployees.savedPreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SavedSharedPreference {

    private const val PREF_name = "name"
    private const val PREF_id = "token"

    private fun getSharedPreferences(ctx: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun setUserData(
        ctx: Context,
        name: String?,
        token: String?
    )
    {
        val editor = getSharedPreferences(ctx).edit()
        editor.putString(PREF_name, name)
        editor.putString(PREF_id, token)
        editor.apply()
        editor.commit()
    }

    fun getUserData(ctx: Context): SharedDataClass {
        return SharedDataClass(
            getSharedPreferences(ctx).getString(PREF_name, ""),
            getSharedPreferences(ctx).getString(PREF_id, "")
        )
    }
}