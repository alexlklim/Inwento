package com.alex.inwento.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.alex.inwento.util.Util;


public class SettingsMng {

    private final SharedPreferences pref;
    public static final String PREF_NAME = Util.APP_NAME;

    public SettingsMng(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public void  setIsRememberMe(Boolean isRememberMe){
        pref.edit().putBoolean(Util.IS_REMEMBER_ME, isRememberMe).apply();
    }

    public Boolean getIsRememberMe(){
        return pref.getBoolean(Util.IS_REMEMBER_ME, false);
    }


    public void setAuthInfo(String firstName, String lastName, String accessToken, String refreshToken) {
        pref.edit()
                .putString(Util.FIRST_NAME, firstName)
                .putString(Util.LAST_NAME, lastName)
                .putString(Util.ACCESS_TOKEN, accessToken)
                .putString(Util.REFRESH_TOKEN, refreshToken)
                .apply();
    }

    public void setLoginDetails(String email, String password){
        pref.edit()
                .putString(Util.EMAIL, email)
                .putString(Util.PASSWORD, password)
                .apply();
    }


    public String getAccessToken() {
        return pref.getString(Util.ACCESS_TOKEN, "");
    }

    public String getRefreshToken() {
        return pref.getString(Util.REFRESH_TOKEN, "");
    }

    public String getEmail(){
        return pref.getString(Util.EMAIL, "");
    }

    public String getPassword(){
        return pref.getString(Util.PASSWORD, "");
    }

    public String getFirstname(){
        return pref.getString(Util.FIRST_NAME, "");
    }
    public String getLastname(){
        return pref.getString(Util.LAST_NAME, "");
    }

}
