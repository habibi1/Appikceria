package com.bkipmlampung.appikceria.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.view.menu.MenuBuilder;

public class StringUtils {
    public static String[] getArrayFromMenu(Context context, int menu_main)
    {
        @SuppressLint("RestrictedApi") Menu menu = new MenuBuilder(context);
        new MenuInflater(context).inflate(menu_main, menu);

        String[] temp = new String[menu.size()];
        for(int i = 0; i < menu.size(); i++)
            temp[i] = menu.getItem(i).getTitle().toString();
        return temp;
    }

    private StringUtils() {
        //No instances.
    }
}
