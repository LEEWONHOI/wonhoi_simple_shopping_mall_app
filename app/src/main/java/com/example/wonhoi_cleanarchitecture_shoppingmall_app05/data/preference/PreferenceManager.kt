package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.preference

import android.content.Context
import android.content.SharedPreferences

/**
 * Data Save and Load class
 */
class PreferenceManager(
    private val context: Context
) {

    companion object {
        const val PREFERENCES_NAME = "wonhoi_cleanArchitecture_shoppingMall-pref "
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_VALUE_BOOLEAN = false
        private const val DEFAULT_VALUE_INT = -1
        private const val DEFAULT_VALUE_LONG = -1L
        private const val DEFAULT_VALUE_FLOAT = -1f

        const val KEY_ID_TOKEN = "ID_TOKEN"
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private val prefs by lazy {
        getPreferences(context)
    }

    private val editor by lazy {
        prefs.edit()
    }

    /**
     * String value save
     * @param context
     * @param key
     * @param value
     */
    fun setString(key: String?, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Boolean value save
     * @param context
     * @param key
     * @param value
     */
    fun setBoolean (key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * int value save
     * @param context
     * @param key
     * @param value
     */
    fun setInt(key: String?, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * long value save
     * @param context
     * @param key
     * @param value
     */
    fun setLong(key: String?, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * float value save
     * @param context
     * @param key
     * @param value
     */
    fun setFloat(key: String?, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    /**
     * String value load
     * @param context
     * @param key
     * @return
     */
    fun getString(key:String?) : String? {
        return prefs.getString(key, DEFAULT_VALUE_STRING)
    }

    /**
     * Boolean value load
     * @param context
     * @param key
     * @return
     */
    fun getBoolean(key: String?) : Boolean? {
        return prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN)
    }

    /**
     * Int value load
     * @param context
     * @param key
     * @return
     */
    fun getInt(key: String?) : Int {
        return prefs.getInt(key, DEFAULT_VALUE_INT)
    }

    /**
     * long value load
     * @param context
     * @param key
     * @return
     */
    fun getLong(key: String?): Long {
        return prefs.getLong(key, DEFAULT_VALUE_LONG)
    }

    /**
     * float value load
     * @param context
     * @param key
     * @return
     */
    fun getFloat(key: String?): Float {
        return prefs.getFloat(key, DEFAULT_VALUE_FLOAT)
    }

    /**
     * Key value delete
     * @param context
     * @param key
     */
    fun removeKey(key:String?) {
        editor.remove(key)
        editor.apply()
    }

    /**
     * All Save data delete
     * @param context
     */
    fun clear() {
        editor.clear()
        editor.apply()
    }

    fun putIdToken(idToken:String) {
        editor.putString(KEY_ID_TOKEN, idToken)
        editor.apply()
    }

    fun getIdToken() : String? {
        return prefs.getString(KEY_ID_TOKEN, null)
    }

    fun removeIdToken() {
        editor.putString(KEY_ID_TOKEN, null)
        editor.apply()
    }

}