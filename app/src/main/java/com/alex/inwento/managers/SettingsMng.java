package com.alex.inwento.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.alex.inwento.http.auth.AuthDTO;
import com.alex.inwento.util.Util;


public class SettingsMng {

    private final SharedPreferences pref;
    public static final String PREF_NAME = Util.APP_NAME;

    public SettingsMng(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public void setServerAddress(String serverAddress) {
        pref.edit().putString(Util.SERVER_ADDRESS, serverAddress).apply();
    }

    public String getServerAddress(){
        return pref.getString(Util.SERVER_ADDRESS, "");
    }


    public void  setIsRememberMe(Boolean isRememberMe){
        pref.edit().putBoolean(Util.IS_REMEMBER_ME, isRememberMe).apply();
    }

    public Boolean getIsRememberMe(){
        return pref.getBoolean(Util.IS_REMEMBER_ME, false);
    }


    public void setAuthInfo(AuthDTO dto) {
        pref.edit()
                .putString(Util.FIRST_NAME, dto.getFirstName())
                .putString(Util.LAST_NAME, dto.getLastName())
                .putString(Util.ACCESS_TOKEN, dto.getAccessToken())
                .putString(Util.REFRESH_TOKEN, dto.getRefreshToken())
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

    public void setIsFilter(boolean bool) {
        pref.edit().putBoolean(Util.IS_FILTER, bool).apply();
    }

    public boolean isFilter(){
        return pref.getBoolean(Util.IS_FILTER, false);
    }
    public void setCodeSettings(String prefix, String suffix, String postfix, Integer length, Integer maxLength, Integer minLength) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Util.CODE_PREFIX, prefix != null ? prefix : "");
        editor.putString(Util.CODE_SUFFIX, suffix != null ? suffix : "");
        editor.putString(Util.CODE_POSTFIX, postfix != null ? postfix : "");
        editor.putInt(Util.CODE_LENGTH, length != null ? length : 0);
        editor.putInt(Util.CODE_MAX_LENGTH, maxLength != null ? maxLength : 0);
        editor.putInt(Util.CODE_MIN_LENGTH, minLength != null ? minLength : 0);
        editor.apply();
    }



    public String getCodePrefix(){
        return pref.getString(Util.CODE_PREFIX, "");
    }
    public String getCodeSuffix(){
        return pref.getString(Util.CODE_SUFFIX, "");
    }
    public String getCodePostfix(){
        return pref.getString(Util.CODE_POSTFIX, "");
    }

    public int getCodeLength(){
        return pref.getInt(Util.CODE_LENGTH, 0);
    }

    public int getCodeMaxLength(){
        return pref.getInt(Util.CODE_MAX_LENGTH, 0);
    }

    public int getCodeMinLength(){
        return pref.getInt(Util.CODE_MIN_LENGTH, 0);
    }





}
