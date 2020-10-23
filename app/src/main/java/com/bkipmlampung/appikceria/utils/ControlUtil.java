package com.bkipmlampung.appikceria.utils;

import android.os.SystemClock;

public class ControlUtil {

    private static long mLastClickTime = 0;

    public static void elapseClick(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }
}
