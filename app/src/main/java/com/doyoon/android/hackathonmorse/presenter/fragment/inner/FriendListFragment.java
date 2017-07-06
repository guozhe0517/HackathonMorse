package com.doyoon.android.hackathonmorse.presenter.fragment.inner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.doyoon.android.hackathonmorse.R;
import com.doyoon.android.hackathonmorse.domain.user.FriendKey;
import com.doyoon.android.hackathonmorse.presenter.fragment.ChatFragment;
import com.doyoon.android.hackathonmorse.presenter.fragment.abst.RecyclerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendListFragment extends RecyclerFragment<FriendKey> {

    private static final String TAG = FriendListFragment.class.getName();

    public static FriendListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FriendListFragment fragment = new FriendListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    List<FriendKey> friendKeyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.dependencyInjectionAndAddListener(view);
        return view;
    }

    public List<FriendKey> getDataList(){
        return this.friendKeyList;
    }



    public void onSearchBtn(){
        String searchFriendName = searchView.getQuery().toString();
        Log.e(TAG, searchFriendName);
    }


    /* Dependency Injection and Add Listener */
    private SearchView searchView;
    private void dependencyInjectionAndAddListener(View view) {

        searchView = (SearchView) view.findViewById(R.id.friend_list_fragment_searchview);

        view.findViewById(R.id.friend_list_fragment_btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchBtn();
            }
        });
    }

    @Override
    public CustomViewHolder throwCustomViewHolder(View view) {
        return new CustomViewHolder(view) {
            ImageView imageView;
            TextView textViewName;
            TextView textViewEmail;

            @Override
            public void updateRecyclerItemView(View view, FriendKey friendKey) {
                // imageView.setImageResource();
                textViewName.setText(friendKey.getName());
            }

            @Override
            public void dependencyInjection(View itemView, FriendKey friendKey) {
                imageView = (ImageView) itemView.findViewById(R.id.item_friendlist_image);
                textViewName = (TextView) itemView.findViewById(R.id.item_friendlist_name);
                // textViewEmail = (TextView)

            }

            @Override
            public void onClick(View v) {
                FriendKey friendKey = super.getT();
                goChatFragment(friendKey.getExistChatRefKey());
            }
        };
    }

    private void goChatFragment(String existChatRefKey) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ChatFragment chatFragment = ChatFragment.newInstance();

        /* Throw Bundle */
        Bundle bundle = new Bundle();
        bundle.putString("CHATKEY", existChatRefKey);
        chatFragment.setArguments(bundle);

        /* begin fragment */
        transaction.add(R.id.main_fragment_container, chatFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public int throwFragmentLayoutResId() {
        return R.layout.fragment_friend_list;
    }

    @Override
    public int throwRecyclerViewResId() {
        return R.id.friend_list_fragment_recyclerView;
    }

    @Override
    public List<FriendKey> throwDataList() {
        return friendKeyList;
    }

    @Override
    public int throwItemLayoutId() {
        return R.layout.item_of_friend_list;
    }
}
