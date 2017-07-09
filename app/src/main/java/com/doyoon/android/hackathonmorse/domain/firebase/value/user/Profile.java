package com.doyoon.android.hackathonmorse.domain.firebase.value.user;

import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseModel;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class Profile extends FirebaseModel {

    private String name;
    private String uid;
    private String imageUrl;
    private String email;

    public Profile() {

    }

    @Override
    public String getValueKey() {
        return null;
    }

    @Override
    public String[] getReferenceKeys() {
        String[] referenceKeys = new String[1];
        referenceKeys[0] = this.uid;
        return referenceKeys;
    }

    @Override
    public String getDbPath() {
        String modelName = getClass().getSimpleName();

        // root/users/?/
        /* Build Reference Key */
        String[] referenceKeys = new String[1];
        referenceKeys[0] = this.uid;
        // buildDbPath(modelName, referenceKeys);
        return null;
    }

    public Profile(String name, String uid) {
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


}
