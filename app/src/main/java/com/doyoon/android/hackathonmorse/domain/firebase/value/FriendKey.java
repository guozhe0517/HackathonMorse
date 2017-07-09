package com.doyoon.android.hackathonmorse.domain.firebase.value;

import android.util.Log;

import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseHelper;
import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseModel;

import java.util.HashMap;
import java.util.List;

import static com.doyoon.android.hackathonmorse.domain.firebase.FirebaseHelper.dbStructureMap;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendKey extends FirebaseModel {

    public String uid;
    public String name;
    public String imageUrl;
    public String email;

    public String existChatRefKey;

    public FriendKey() {

    }

    public FriendKey(String uid, String name, String imageUrl, String email, String existChatRefKey) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.email = email;
        this.existChatRefKey = existChatRefKey;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExistChatRefKey() {
        return existChatRefKey;
    }

    public void setExistChatRefKey(String existChatRefKey) {
        this.existChatRefKey = existChatRefKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "FriendKey{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", email='" + email + '\'' +
                ", existChatRefKey='" + existChatRefKey + '\'' +
                '}';
    }

    @Override
    public String getModelKey() {
        return null;
    }

    @Override
    public String getModelDir(String... params) {
        HashMap<String, String> modelAttributeMap = dbStructureMap.get(getClass().getSimpleName().toLowerCase());
        String modelDir = modelAttributeMap.get("modelDir");

        List<String> paramList = FirebaseHelper.findParams(modelDir);

        if (params.length < paramList.size()) {
            Log.e("FirebaseModel", "Model Dir : " + modelDir);
            throw new NullPointerException("Not enough params, if you want to access this Model, you need more param(key)..");
        }

        for(int i =0; i <paramList.size(); i ++) {
            modelDir = modelDir.replace(paramList.get(i), params[i]);
        }
        /*
        for(int i=0; i < needParamNum; i++) {
            // Log.e( null -> need this value attribute refer="";
            //replace (params[i]) ;
        }
        */

        return modelDir;
    }
}
