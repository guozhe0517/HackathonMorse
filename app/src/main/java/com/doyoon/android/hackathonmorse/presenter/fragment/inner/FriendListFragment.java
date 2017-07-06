package com.doyoon.android.hackathonmorse.presenter.fragment.inner;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendListFragment extends Fragment {

    public static FriendListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FriendListFragment fragment = new FriendListFragment();
        fragment.setArguments(args);
        return fragment;
    }





}
