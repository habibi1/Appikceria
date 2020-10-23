package com.bkipmlampung.appikceria.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private Context context;
    private String tanggal;

    TimeUtil(Context context){
        this.context = context;
    }

    public static String getTanggal(){
        @SuppressLint("SimpleDateFormat")SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
        return dateFormat.format(new Date());
    }

    public static String getSemester(){
        @SuppressLint("SimpleDateFormat")SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
        String date = dateFormat.format(new Date());
        String[] dates = date.split("-");

        int month = Integer.parseInt(dates[1]);
        String semester = "";

        if (month >= 1 && month <= 6)
            semester = "Genap";
        else if (month >=7)
            semester = "Ganjil";
        else
            Log.d("error", "date error");

        return semester;
    }

    public static String getDateDD(String tanggal){
        return tanggal.substring(8,10);
    }

    public static String getDateMMMM(String tanggal){
        String mm = tanggal.substring(5,7);
        boolean status = false;

        switch (mm){
            case "01":
                mm = "Januari";
                break;
            case "02":
                mm = "Februari";
                break;
            case "03":
                mm = "Maret";
                break;
            case "04":
                mm = "April";
                break;
            case "05":
                mm = "Mei";
                break;
            case "06":
                mm = "Juni";
                break;
            case "07":
                mm = "Juli";
                break;
            case "08":
                mm = "Agustus";
                break;
            case "09":
                mm = "September";
                break;
            case "10":
                mm = "Oktober";
                break;
            case "11":
                mm = "November";
                break;
            case "12":
                mm = "Desember";
                break;
            default:
                status = true;
                break;
        }

        return mm;
    }

    public static String getDateYYYY(String tanggal){
        return tanggal.substring(0,4);
    }

    public static String getDateDDMMMMYYYY(String tanggal) {
        String yyyy = tanggal.substring(0,4);
        String mm = tanggal.substring(5,7);
        String dd = tanggal.substring(8,10);
        boolean status = false;

        switch (mm){
            case "01":
                mm = "Januari";
                break;
            case "02":
                mm = "Februari";
                break;
            case "03":
                mm = "Maret";
                break;
            case "04":
                mm = "April";
                break;
            case "05":
                mm = "Mei";
                break;
            case "06":
                mm = "Juni";
                break;
            case "07":
                mm = "Juli";
                break;
            case "08":
                mm = "Agustus";
                break;
            case "09":
                mm = "September";
                break;
            case "10":
                mm = "Oktober";
                break;
            case "11":
                mm = "November";
                break;
            case "12":
                mm = "Desember";
                break;
            default:
                status = true;
                break;
        }

        if (status)
            return tanggal;
        else
            return dd + " " + mm + " " + yyyy;
    }

}
