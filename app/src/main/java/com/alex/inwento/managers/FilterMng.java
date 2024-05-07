package com.alex.inwento.managers;

import android.util.Log;

public class FilterMng {


    public static String filteringData(String code, SettingsMng settingsMng) {
        int length = code.length();
        if ((settingsMng.getCodeLength() != 0 && length != settingsMng.getCodeLength()) ||
                (settingsMng.getCodeMinLength() != 0  && length < settingsMng.getCodeMinLength()) ||
                (settingsMng.getCodeMaxLength() != 0 && length > settingsMng.getCodeMaxLength())){
            return null;
        }

        if (!settingsMng.getCodePrefix().equals("") && !code.startsWith(settingsMng.getCodePrefix())) return null;
        if (!settingsMng.getCodeSuffix().equals("") && !code.contains(settingsMng.getCodeSuffix())) return null;
        if (!settingsMng.getCodePostfix().equals("") && !code.endsWith(settingsMng.getCodePostfix())) return null;

        return code;
    }
}
