package com.doyoon.android.hackathonmorse.domain.firebase.value;

import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseModel;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class UserProfile extends FirebaseModel {

    private String name;
    private String uid;
    private String imageUrl;
    private String email;

    public UserProfile() {

    }

    public UserProfile(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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


    @Override
    public String getModelKey() {
        return null;
    }

    @Override
    public String getModelDir(String... params) {
        return null;
    }
}
