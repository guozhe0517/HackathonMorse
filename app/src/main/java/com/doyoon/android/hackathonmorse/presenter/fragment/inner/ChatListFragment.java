package com.doyoon.android.hackathonmorse.presenter.fragment.inner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doyoon.android.hackathonmorse.R;
import com.doyoon.android.hackathonmorse.domain.firebase.value.ChatKey;
import com.doyoon.android.hackathonmorse.presenter.fragment.ChatFragment;
import com.doyoon.android.hackathonmorse.presenter.fragment.abst.RecyclerFragment;
import com.doyoon.android.hackathonmorse.util.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class ChatListFragment extends RecyclerFragment<ChatKey> {

    private static final String TAG = ChatListFragment.class.getName();
    private List<ChatKey> chatKeyList = new ArrayList<>();

    public static ChatListFragment newInstance() {

        Bundle args = new Bundle();

        ChatListFragment fragment = new ChatListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        return view;
    }

    public List<ChatKey> getDataList(){
        return this.chatKeyList;
    }

    private void goChatFragment(String existChatRefKey) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ChatFragment chatFragment = ChatFragment.newInstance();

        /* Throw Bundle */
        Bundle bundle = new Bundle();
        bundle.putString(Const.CHAT_BUNDLE_KEY, existChatRefKey);
        chatFragment.setArguments(bundle);

        /* begin fragment */
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
            public void dependencyInjection(View itemView, ChatKey chatKey) {
                imageView = (ImageView) itemView.findViewById(R.id.item_chatlist_image);
                textViewTitle = (TextView) itemView.findViewById(R.id.item_chatlist_title);
                textViewDate = (TextView) itemView.findViewById(R.id.item_chatlist_current_time);
            }

            @Override
            public void updateRecyclerItemView(View view, ChatKey chatKey) {
                // imageView.setImageResource();
                textViewTitle.setText(chatKey.getFriendName());
                textViewTitle.setText(chatKey.getCurrentChatTime());
            }

            @Override
            public void onClick(View v) {
                ChatKey chatKey = getT();
                goChatFragment(chatKey.getChatRefKey());
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
    public List<ChatKey> throwDataList() {
        return chatKeyList;
    }

    @Override
    public int throwItemLayoutId() {
        return R.layout.item_of_chat_list;
    }
}
