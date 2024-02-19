package com.alex.asset.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;



public class DateService {

    public static LocalDateTime getDateNow() {
        return LocalDateTime.now();
    }

    public static LocalDateTime addOneDayToDate(LocalDateTime dateTime) {
        return dateTime.plusDays(1);
    }

    public static Date addFiveMinutesToDate(LocalDateTime dateTime) {
        return convertToDate(dateTime.plusMinutes(5));
    }


    public static Date convertToDate(LocalDateTime localDateTime){
       return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }




}
