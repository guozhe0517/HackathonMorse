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

import com.doyoon.android.hackathonmorse.MainActivity;
import com.doyoon.android.hackathonmorse.R;
import com.doyoon.android.hackathonmorse.domain.User;
import com.doyoon.android.hackathonmorse.domain.user.ChatKey;
import com.doyoon.android.hackathonmorse.domain.user.FriendKey;
import com.doyoon.android.hackathonmorse.presenter.fragment.inner.ChatListFragment;
import com.doyoon.android.hackathonmorse.presenter.fragment.inner.FriendListFragment;
import com.doyoon.android.hackathonmorse.util.Const;
import com.doyoon.android.hackathonmorse.util.converter.GsonConv;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendAndChatListFragment extends Fragment {

    private String USER_ROOT = "user";
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


        /* Firebase Database */
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(USER_ROOT).child(MainActivity.UID + "9");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = null;

                if (!dataSnapshot.exists()) {   // 사용자가 없으면 새 사용자를 등록한다.

                    // user = createDummyUser();
                    user = new User("[]", "[]");
                    myRef.setValue(user);

                    Log.e(TAG, "사용자를 새로 등록했습니다.");
                } else {
                    // 사용자를 새로 등록할때는..위 코드에서 사용자를 등록하기 전에.... 없던 키를 가져와버리네...
                    user = dataSnapshot.getValue(User.class);
                }

                if (user == null) {
                    Log.e(TAG, "User가 null 입니다.");
                }

                // todo 여긴 상황이 더심각하네... 변경점이 하나만 있어도 계속 갱신하게 되네....
                if (!Const.JSON_EMPTY_ARRAY.equals(user.getJsonFriendKeyList())) {
                    List<FriendKey> friendKeyList = GsonConv.getInstance().fromJson(user.getJsonFriendKeyList(), new TypeToken<ArrayList<FriendKey>>(){}.getType());
                    friendListFragment.getDataList().clear();
                    for (FriendKey friendKey : friendKeyList) {
                        friendListFragment.getDataList().add(friendKey);
                    }
                    friendListFragment.notifyDataSetChanged();
                }

                if (!Const.JSON_EMPTY_ARRAY.equals(user.getJsonChatKeyList())) {
                    List<ChatKey> chatKeyList = GsonConv.getInstance().fromJson(user.getJsonChatKeyList(), new TypeToken<ArrayList<ChatKey>>(){}.getType());
                    chatListFragment.getDataList().clear();
                    for (ChatKey chatKey : chatKeyList) {
                        chatListFragment.getDataList().add(chatKey);
                    }
                    chatListFragment.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private User createDummyUser(){
         /* dummy data */
        List<FriendKey> friendKeyList = new ArrayList<FriendKey>();
        friendKeyList.add(new FriendKey("김도윤", "test image url", "김도윤@goo.com", "-KoM6cNAbEX4WZJsKTJk"));
        friendKeyList.add(new FriendKey("곽철", "test image url", "곽철@goo.com", "-KoMC1vxvNMAAjSF2s2C"));
        String jsonFriendKeyList = GsonConv.getInstance().toJson(friendKeyList);

        List<ChatKey> chatKeyList = new ArrayList<ChatKey>();
        String jsonChatKeyList = GsonConv.getInstance().toJson(chatKeyList);

        User user = new User();
        user.setJsonFriendKeyList(jsonFriendKeyList);
        user.setJsonChatKeyList(jsonChatKeyList);

        return user;
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
