package de.hhn.softwarelabor.pawswipeapp.utils

import android.content.Context

class AppData {
    companion object{
        private const val SHARED_PREFS_NAME = "MySharedPrefs"
        private const val ID_KEY = "IdKey"
        private const val MAIL_KEY = "MailKey"
        private const val PASSWORD_KEY = "PasswordKey"
        private const val DISCRIMINATOR_KEY = "DiscriminatorKey"
    
        fun getID(context: Context): Int {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPrefs.getInt(ID_KEY, 0)
        }
        fun setID(context: Context, id: Int){
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putInt(ID_KEY, id)
            editor.apply()
        }
        fun getMail(context: Context): String {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPrefs.getString(MAIL_KEY, "") ?:""
        }
        fun setMail(context: Context, mail: String){
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString(MAIL_KEY, mail)
            editor.apply()
        }
        fun getPassword(context: Context): String {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPrefs.getString(PASSWORD_KEY, "") ?:""
        }
        fun setPassword(context: Context, password: String){
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString(PASSWORD_KEY, password)
            editor.apply()
        }
        fun getDiscriminator(context: Context): String {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPrefs.getString(DISCRIMINATOR_KEY, "") ?:""
        }
        fun setDiscriminator(context: Context, discriminator: String){
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString(DISCRIMINATOR_KEY, discriminator)
            editor.apply()
        }
    }
}