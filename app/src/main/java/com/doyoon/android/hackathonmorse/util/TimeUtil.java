package com.doyoon.android.hackathonmorse.util;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by DOYOON on 7/9/2017.
 */

public class TimeUtil {


    /* 2017. 7. 9. 오후 3:37:53 */
    public static String getCurrentTime(){
        return DateFormat.getDateTimeInstance().format(new Date());
    }
}
