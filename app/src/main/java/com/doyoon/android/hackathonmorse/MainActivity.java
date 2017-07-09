package com.doyoon.android.hackathonmorse;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.doyoon.android.hackathonmorse.domain.dao.RemoteDao;
import com.doyoon.android.hackathonmorse.presenter.fragment.FriendAndChatListFragment;

public class MainActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get UID */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            RemoteDao.MYUID = bundle.getString("UID");
            RemoteDao.MYNAME = bundle.getString("name");
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
