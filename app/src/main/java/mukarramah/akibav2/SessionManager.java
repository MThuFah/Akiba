package mukarramah.akibav2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_LEMBAGA = "lembaga";
    public static final String KEY_NAME = "nama";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_SANDI = "sandi";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_HP = "hp";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String lembaga, String nama, String alamat, String sandi, String email, String hp){
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_LEMBAGA, lembaga);
        editor.putString(KEY_NAME, nama);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_SANDI, sandi);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_HP, hp);

        editor.commit();
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_LEMBAGA, pref.getString(KEY_LEMBAGA, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_ALAMAT, pref.getString(KEY_ALAMAT, null));
        user.put(KEY_SANDI, pref.getString(KEY_SANDI, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_HP, pref.getString(KEY_HP, null));

        return user;
    }
    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void checkLogin() {
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }


}
