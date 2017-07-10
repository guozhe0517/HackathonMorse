package com.doyoon.android.hackathonmorse.domain.dummy;

import com.doyoon.android.hackathonmorse.domain.firebase.value.UserProfile;

/**
 * Created by DOYOON on 7/9/2017.
 */

public class DummyDao {


    public static UserProfile getUserProfile(){

        UserProfile userProfile = new UserProfile();
        userProfile.setUid("test_uid");
        userProfile.setName("test_name");
        userProfile.setEmail("miraee05@naver.com" + System.currentTimeMillis());

        return userProfile;
    }


}
