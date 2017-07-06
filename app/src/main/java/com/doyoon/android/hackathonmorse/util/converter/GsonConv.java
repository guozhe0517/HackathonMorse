package com.doyoon.android.hackathonmorse.util.converter;

import com.google.gson.Gson;

/**
 * Created by DOYOON on 6/25/2017.
 */

public class GsonConv {

    // new TypeToken<ArrayList<Chat>>(){}.getType()

    public static Gson instance = null;

    public static Gson getInstance(){
        if(instance == null){
            instance = new Gson();
        }
        return instance;
    }

    public static String objToString(Object obj){
        return instance.toJson(obj);
    }

    public static <T> T stringToObj(String str, Class<T> classOfT) {
        return instance.fromJson(str, classOfT);
    }
}
