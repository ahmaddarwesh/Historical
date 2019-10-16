package com.superdar.historical

import android.content.Context

class stor {

    companion object {
        fun saveInt(conx: Context, key: String, value: Int) {
            val shareP = conx.getSharedPreferences("Data", 0)
            val editor = shareP.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun getInt(conx: Context, key: String): Int {
            val shareP = conx.getSharedPreferences("Data", 0)
            return shareP.getInt(key, 0)
        }
    }
}