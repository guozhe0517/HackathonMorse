package com.doyoon.android.hackathonmorse.presenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doyoon.android.hackathonmorse.R;
import com.doyoon.android.hackathonmorse.presenter.fragment.inner.ChatListFragment;
import com.doyoon.android.hackathonmorse.presenter.fragment.inner.FriendListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendAndChatListFragment extends Fragment {

    public static FriendAndChatListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FriendAndChatListFragment fragment = new FriendAndChatListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_and_chat_list, container, false);

        /* Dependency Injection */
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.friend_and_chat_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.friend_and_chat_tablayout);

        /*
        tabLayout.addTab(tabLayout.newTab().setText("One"));
        tabLayout.addTab(tabLayout.newTab().setText("TWo"));
        */

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ChatListFragment.newInstance());
        fragmentList.add(FriendListFragment.newInstance());

        CustomPageAdapter customPageAdapter = new CustomPageAdapter(getFragmentManager(), fragmentList);

        viewPager.setAdapter(customPageAdapter);

        /* Tab Layout Listener */
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        return view;
    }

    class CustomPageAdapter extends FragmentStatePagerAdapter {

        private List<android.support.v4.app.Fragment> fragmentList;

        public CustomPageAdapter(FragmentManager fm, List<android.support.v4.app.Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
