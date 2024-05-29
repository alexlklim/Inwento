package com.alex.asset.utils.dictionaries;

public class UtilsSecurity {

    public  final static String[] PUBLIC_ROUTES = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh",
            "/api/v1/auth/pw/forgot",
            "/api/v1/auth/pw/recovery/**",
            "/api/core/get",
            "/swagger-ui/**",
            "/v3/api-docs/**",
    };


    public static  final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static  final String ENDPOINT_RECOVERY = "http://localhost:9091/api/auth/pw/recovery/";
    public static  final String ENDPOINT_LOGIN = "http://localhost:9091/api/auth/login";

}
