package com.doyoon.android.hackathonmorse.domain.firebase;

/**
 * Created by DOYOON on 7/8/2017.
 */

public abstract class FirebaseModel {

    public abstract String getValueKey();
    public abstract String[] getReferenceKeys();
    public abstract String getDbPath();
}
