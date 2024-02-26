package com.alex.asset.utils;

public class Utils {

    public final static String[] PUBLIC_ROUTES = {
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh",
            "/api/auth/pw/forgot",
            "/api/core/get",
            "/api/auth/pw/recovery/**"
    };




    public static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static final String ENDPOINT_RECOVERY = "http://localhost:9091/api/auth/pw/recovery/";
    public static final String ENDPOINT_LOGIN = "http://localhost:9091/api/auth/login";


}
