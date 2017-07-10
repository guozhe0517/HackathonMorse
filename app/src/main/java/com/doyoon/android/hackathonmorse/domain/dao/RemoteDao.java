package com.doyoon.android.hackathonmorse.domain.dao;

import android.util.Log;

import com.doyoon.android.hackathonmorse.domain.firebase.value.UserChatroom;
import com.doyoon.android.hackathonmorse.domain.firebase.value.Friend;
import com.doyoon.android.hackathonmorse.util.Const;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by DOYOON on 7/7/2017.
 */

public class RemoteDao {

    // public static String MYUID = "";
    public static String MYUID = "aaa_uid";    /* This code is for dummy, remove this code */
    public static String MYNAME = "kim";

    public static void insertFriendKey(Friend friend){
        Log.e("TAG", friend.toString());
        FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_USER_ROOT).child(MYUID).child(Const.FRIEND_KEY_REF).child(friend.getUid()).setValue(friend);
    }

    public static void insertChatKey(UserChatroom userChatroom) {
        FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_USER_ROOT).child(MYUID).child(Const.CHAT_KEY_REF).child(userChatroom.getChatRefKey()).setValue(userChatroom);
    }


}
