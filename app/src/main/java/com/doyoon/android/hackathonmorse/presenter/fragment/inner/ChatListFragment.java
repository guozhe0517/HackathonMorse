package com.doyoon.android.hackathonmorse.presenter.fragment.inner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doyoon.android.hackathonmorse.R;
import com.doyoon.android.hackathonmorse.domain.firebase.value.UserChatroom;
import com.doyoon.android.hackathonmorse.presenter.fragment.ChatFragment;
import com.doyoon.android.hackathonmorse.presenter.fragment.abst.RecyclerFragment;
import com.doyoon.android.hackathonmorse.util.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class ChatListFragment extends RecyclerFragment<UserChatroom> {

    private static final String TAG = ChatListFragment.class.getSimpleName();
    private List<UserChatroom> userChatroomList = new ArrayList<>();

    public static ChatListFragment newInstance() {

        Bundle args = new Bundle();

        ChatListFragment fragment = new ChatListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public List<UserChatroom> getDataList(){
        return this.userChatroomList;
    }

    private void goChatFragment(UserChatroom userChatroom) {

        /* Prepare Chat Fragment and Bundle */
        ChatFragment chatFragment = ChatFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(Const.CHAT_KEY_IN_BUNDLE, userChatroom.getKey());
        bundle.putString(Const.FRIEND_KEY_IN_BUNDLE, userChatroom.getFrinedKey());
        //bundle.putString(Const.FRIEND_KEY_IN_BUNDLE, friendUid);
        chatFragment.setArguments(bundle);

        /* begin fragment */
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_fragment_container, chatFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public CustomViewHolder throwCustomViewHolder(View view) {

        return new CustomViewHolder(view) {
            private ImageView imageView;
            private TextView textViewTitle;
            private TextView textViewDate;

            @Override
            public void dependencyInjection(View itemView, UserChatroom userChatroom) {
                imageView = (ImageView) itemView.findViewById(R.id.item_chatlist_image);
                textViewTitle = (TextView) itemView.findViewById(R.id.item_chatlist_title);
                textViewDate = (TextView) itemView.findViewById(R.id.item_chatlist_current_time);
            }

            @Override
            public void updateRecyclerItemView(View view, UserChatroom userChatroom) {
                // imageView.setImageResource();
                //textViewTitle.setText(userChatroom.getFriendName());
                //textViewTitle.setText(userChatroom.getCurrentChatTime());

                /* temp */
                textViewTitle.setText(userChatroom.getKey());
            }

            @Override
            public void onClick(View v) {
                UserChatroom userChatroom = getT();
                goChatFragment(userChatroom);
            }
        };
    }

    @Override
    public int throwFragmentLayoutResId() {
        return R.layout.fragment_chat_list;
    }

    @Override
    public int throwRecyclerViewResId() {
        return R.id.chat_list_fragment_recyclerView;
    }

    @Override
    public List<UserChatroom> throwDataList() {
        return userChatroomList;
    }

    @Override
    public int throwItemLayoutId() {
        return R.layout.item_of_chat_list;
    }
}
