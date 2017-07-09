package com.doyoon.android.hackathonmorse.domain.dao;

import android.util.Log;

import com.doyoon.android.hackathonmorse.domain.firebase.value.ChatKey;
import com.doyoon.android.hackathonmorse.domain.firebase.value.FriendKey;
import com.doyoon.android.hackathonmorse.util.Const;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by DOYOON on 7/7/2017.
 */

public class RemoteDao {

    // public static String MYUID = "";
    public static String MYUID = "aaa_uid";    /* This code is for dummy, remove this code */
    public static String MYNAME = "kim";

    public static void insertFriendKey(FriendKey friendKey){
        Log.e("TAG", friendKey.toString());
        FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_USER_ROOT).child(MYUID).child(Const.FRIEND_KEY_REF).child(friendKey.getUid()).setValue(friendKey);
    }

    public static void insertChatKey(ChatKey chatKey) {
        FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_USER_ROOT).child(MYUID).child(Const.CHAT_KEY_REF).child(chatKey.getChatRefKey()).setValue(chatKey);
    }


}
