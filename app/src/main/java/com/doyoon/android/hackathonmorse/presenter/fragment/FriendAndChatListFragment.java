package com.doyoon.android.hackathonmorse.presenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doyoon.android.hackathonmorse.R;
import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseHelper;
import com.doyoon.android.hackathonmorse.domain.firebase.value.UserChatroom;
import com.doyoon.android.hackathonmorse.domain.firebase.value.Friend;
import com.doyoon.android.hackathonmorse.presenter.fragment.inner.ChatListFragment;
import com.doyoon.android.hackathonmorse.presenter.fragment.inner.FriendListFragment;
import com.doyoon.android.hackathonmorse.presenter.status.CurrentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendAndChatListFragment extends Fragment {

    public static final String TAG = FriendAndChatListFragment.class.getSimpleName();

    FriendListFragment friendListFragment;
    ChatListFragment chatListFragment;

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

        final List<Fragment> fragmentList = new ArrayList<>();
        friendListFragment = FriendListFragment.newInstance();
        fragmentList.add(friendListFragment);
        chatListFragment = ChatListFragment.newInstance();
        fragmentList.add(chatListFragment);

        CustomPageAdapter customPageAdapter = new CustomPageAdapter(getFragmentManager(), fragmentList);

        viewPager.setAdapter(customPageAdapter);

        /* Tab Layout Listener */
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        this.addFirebaseListener();

        return view;
    }

    private void addFirebaseListener(){
        // todo how to close when fragment is closed
        /* Fire Base Friend Key List Listener */
        String friendModelPath = FirebaseHelper.getModelDir("friend", CurrentUser.getUid());
        DatabaseReference friendModelRef = FirebaseDatabase.getInstance().getReference(friendModelPath);
        Log.e(TAG, "Add Friend Model Key Listener : " + friendModelPath);
        friendModelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendListFragment.getDataList().clear();           // todo need more efficiency
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Friend friend = item.getValue(Friend.class);
                    friendListFragment.getDataList().add(friend);
                }
                Log.e(TAG, "How many " + friendListFragment.getDataList().size());
                friendListFragment.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /* Fire Base Chat Key List Listener */
        String chatModelPath = FirebaseHelper.getModelDir("userchatroom", CurrentUser.getUid());
        Log.e(TAG, "Add Chat Model Key Listener : " + chatModelPath);
        FirebaseDatabase.getInstance().getReference(chatModelPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatListFragment.getDataList().clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    UserChatroom userChatroom = item.getValue(UserChatroom.class);
                    chatListFragment.getDataList().add(userChatroom);
                }
                chatListFragment.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class CustomPageAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragmentList;

        public CustomPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
