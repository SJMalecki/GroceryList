package pl.sjmprofil.grocerylist

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

class PrefConfig(private val context: Context) {

    private val sharedPreferences = context
        .getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE)


    fun writeLoginStatus(status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(context.getString(R.string.pref_login_status), status)
        editor.apply()
    }

    fun readLoginStatus(): Boolean {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false)
    }

    fun writeName(name: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(context.getString(R.string.pref_user_name), name)
        editor.apply()
    }

    fun readName(): String {
        return sharedPreferences.getString(context.getString(R.string.pref_user_name), "JsonObject")
    }

    fun displayToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}