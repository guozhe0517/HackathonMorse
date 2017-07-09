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
import com.doyoon.android.hackathonmorse.domain.dao.RemoteDao;
import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseHelper;
import com.doyoon.android.hackathonmorse.domain.firebase.value.ChatKey;
import com.doyoon.android.hackathonmorse.domain.firebase.value.FriendKey;
import com.doyoon.android.hackathonmorse.domain.firebase.value.user.Profile;
import com.doyoon.android.hackathonmorse.presenter.fragment.inner.ChatListFragment;
import com.doyoon.android.hackathonmorse.presenter.fragment.inner.FriendListFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendAndChatListFragment extends Fragment {

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

        Log.e(TAG, "Load Strat DB Structure : " + DateFormat.getDateTimeInstance().format(new Date()));
        FirebaseHelper.loadDbStructure(getContext());
        FirebaseHelper.Dao.insert(new ChatKey());
        FirebaseHelper.Dao.insert(new FriendKey());
        Log.e(TAG, "Load End DB Structure : " + DateFormat.getDateTimeInstance().format(new Date()));

        /* Firebase Database User Profile */
        /*  다시켜면 RemoteDao.MYUID의 값이 null 이네.... 서비스나 다른곳에 저장을 해둬야 하는구만 */
        String refPath = "users/" + RemoteDao.MYUID + "/signup";
        final DatabaseReference signupRef = FirebaseDatabase.getInstance().getReference(refPath);
        signupRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {   // 사용자가 없으면 새 사용자를 등록한다.

                    // insert signup true
                    signupRef.setValue(true);

                    // insert default profile
                    Profile profile = new Profile(RemoteDao.MYNAME, RemoteDao.MYUID);
                    FirebaseHelper.Dao.insert(profile);
                    Log.e(TAG, "사용자를 새로 등록했습니다.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        /* Fire Base Friend Key List Listener */
        /*
        DatabaseReference friendKeyRef = FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_USER_ROOT).child(RemoteDao.MYUID).child(Const.FRIEND_KEY_REF);
        friendKeyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendListFragment.getDataList().clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    FriendKey friendKey = item.getValue(FriendKey.class);
                    friendListFragment.getDataList().add(friendKey);
                }
                friendListFragment.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
        /* Fire Base Chat Key List Listener */
        /*
        DatabaseReference chatKeyRef = FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_USER_ROOT).child(RemoteDao.MYUID).child(Const.CHAT_KEY_REF);
        chatKeyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatListFragment.getDataList().clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    ChatKey friendKey = item.getValue(ChatKey.class);
                    chatListFragment.getDataList().add(friendKey);
                }
                chatListFragment.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */

        return view;
    }

    private Profile createDummyUser(){
         /* dummy data
        List<FriendKey> friendKeyList = new ArrayList<FriendKey>();
        friendKeyList.add(new FriendKey("김도윤", "test image url", "김도윤@goo.com", "-KoM6cNAbEX4WZJsKTJk"));
        friendKeyList.add(new FriendKey("곽철", "test image url", "곽철@goo.com", "-KoMC1vxvNMAAjSF2s2C"));
        String jsonFriendKeyList = GsonConv.getInstance().toJson(friendKeyList);

        List<ChatKey> chatKeyList = new ArrayList<ChatKey>();
        String jsonChatKeyList = GsonConv.getInstance().toJson(chatKeyList);
        */
        Profile profile = new Profile(RemoteDao.MYNAME, RemoteDao.MYUID);
        return profile;
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
