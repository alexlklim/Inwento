package com.alex.asset.utils;

public class Utils {

    public final static String[] PUBLIC_ROUTES = {
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/activate/**"
    };


    public final static String[] ADMIN_ROUTES = {
            "/api/admin"
    };

    public final static String[] CLIENT_ROUTES = {
            "/api/client"
    };


    public static final String ACTIVATE_TOKEN = "a8d77363-50e6-4650-8c75-6e1a7eafa2ec";

    public static final String SECRET_KEY = "rdtfyghujikomlHYUTGRFEWEvsaaD";


}
