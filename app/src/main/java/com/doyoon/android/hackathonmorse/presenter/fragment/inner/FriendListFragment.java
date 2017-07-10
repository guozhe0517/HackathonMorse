package com.doyoon.android.hackathonmorse.presenter.fragment.inner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.doyoon.android.hackathonmorse.R;
import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseDao;
import com.doyoon.android.hackathonmorse.domain.firebase.value.Friend;
import com.doyoon.android.hackathonmorse.domain.firebase.value.UserProfile;
import com.doyoon.android.hackathonmorse.presenter.fragment.ChatFragment;
import com.doyoon.android.hackathonmorse.presenter.fragment.abst.RecyclerFragment;
import com.doyoon.android.hackathonmorse.presenter.status.CurrentUser;
import com.doyoon.android.hackathonmorse.util.Const;
import com.doyoon.android.hackathonmorse.util.converter.ConvString;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendListFragment extends RecyclerFragment<Friend> {

    private static final String TAG = FriendListFragment.class.getSimpleName();
    private List<Friend> friendList = new ArrayList<>();

    public static FriendListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FriendListFragment fragment = new FriendListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.dependencyInjectionAndAddListener(view);
        return view;
    }

    public void onSearchBtn(){
        String email = searchView.getQuery().toString();
        String commaEmail = ConvString.string2comma(email);

        // todo Freind Key를 체크하는 .... 다른 데이터베이스를 만드는게 좋을 것 같긴한데...
        FirebaseDatabase.getInstance().getReference("users/" + commaEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Friend friend = new Friend(dataSnapshot.getKey());
                    UserProfile friendProfile = dataSnapshot.getValue(UserProfile.class); // 친구 Profile이 없을수도 있다. 기본값은 null
                    friend.fetchInfo(friendProfile);
                    if (friend.getEmail() == null) {
                        friend.setEmail(dataSnapshot.getKey());
                    }
                    FirebaseDao.insert(friend, CurrentUser.getUid());
                } else {
                    Toast.makeText(getActivity(), "검색된 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<Friend> getDataList(){
        return this.friendList;
    }

    private void goChatFragment(Friend friend) {
        // String friendUid = friend.getUid();
        /* chat ref key가 null 이더라도 일단 넘긴다... */

        /* Prepare Chat Fragment and Bundle */
        ChatFragment chatFragment = ChatFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(Const.CHAT_KEY_IN_BUNDLE, friend.getExistChatKey());
        bundle.putString(Const.FRIEND_KEY_IN_BUNDLE, friend.getKey());
        //bundle.putString(Const.FRIEND_KEY_IN_BUNDLE, friendUid);
        chatFragment.setArguments(bundle);

        /* begin fragment */
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_fragment_container, chatFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    /* For Recycler Fragment */
    @Override
    public CustomViewHolder throwCustomViewHolder(View view) {
        return new CustomViewHolder(view) {
            ImageView imageView;
            TextView textViewName;
            TextView textViewEmail;

            @Override
            public void updateRecyclerItemView(View view, Friend friend) {
                // imageView.setImageResource();
                setFriendName(textViewName, friend);
            }

            @Override
            public void dependencyInjection(View itemView, Friend friend) {
                imageView = (ImageView) itemView.findViewById(R.id.item_friendlist_image);
                textViewName = (TextView) itemView.findViewById(R.id.item_friendlist_name);
                // textViewEmail = (TextView)

            }

            @Override
            public void onClick(View v) {
                Friend friend = super.getT();
                goChatFragment(friend);
            }
        };
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
    public List<Friend> throwDataList() {
        return friendList;
    }

    @Override
    public int throwItemLayoutId() {
        return R.layout.item_of_friend_list;
    }

    private void setFriendName(TextView textViewName, Friend friend){
        if (friend.getName() != null) {
            textViewName.setText(friend.getName());
            return;
        }

        if (friend.getEmail() != null) {
            textViewName.setText(friend.getEmail());
            return;
        }

        textViewName.setText("No name...");
    }
}
