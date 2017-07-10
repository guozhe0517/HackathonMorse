package com.doyoon.android.hackathonmorse.presenter.status;

/**
 * Created by DOYOON on 7/9/2017.
 */

public class CurrentUser {

    private static String uid;

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        CurrentUser.uid = uid;
    }
}
