package com.doyoon.android.hackathonmorse;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.doyoon.android.hackathonmorse.presenter.fragment.FriendAndChatListFragment;

public class MainActivity extends FragmentActivity {

    public static String UID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get UID */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MainActivity.UID = bundle.getString("UID");
        } else {    /* This code is for dummy, remove this code */
            MainActivity.UID = "asfrom30";
        }

        /* Search in DB, unless finish */



        this.startFragment(FriendAndChatListFragment.newInstance());


    }

    public void startFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    public void goFragment(Fragment fragment) {
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goPrevFragment(){
        FragmentManager manager = this.getSupportFragmentManager();
        manager.popBackStack();
    }
}
