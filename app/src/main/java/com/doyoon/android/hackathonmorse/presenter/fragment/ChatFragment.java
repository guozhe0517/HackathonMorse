package com.doyoon.android.hackathonmorse.presenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.doyoon.android.hackathonmorse.R;
import com.doyoon.android.hackathonmorse.domain.Chat;
import com.doyoon.android.hackathonmorse.domain.RemoteChatObj;
import com.doyoon.android.hackathonmorse.presenter.fragment.abst.RecyclerFragment;
import com.doyoon.android.hackathonmorse.util.converter.GsonConv;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class ChatFragment extends RecyclerFragment<Chat> {
    public static String TAG = ChatFragment.class.getName();

    private String CHAT_ROOT = "chat";
    private DatabaseReference chatRef;
    private List<Chat> chatList = new ArrayList<>();

    public static ChatFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* Dependency Injection */
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.dependencyInejctionAndAddListener(view);

        // 채팅방 유저가 두명있어야 한다.
        // from, to
        // 채팅 기록이 list<String>으로 쌓이게 된다....

        // 문제는 리스너가 계속 달려 있으면 안될거 같은데.... Fragment는 계속해서 new 가 되므로...

        boolean isNewchat = true;
        String chatRefKey = "";

        if(isNewchat){
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference(CHAT_ROOT).child("bookinfolist");
            chatRefKey = rootRef.push().getKey();
        } else {
             // get Reference String from before fragment....
            // chatRefKey =
            // chatList
        }

        chatRef = FirebaseDatabase.getInstance().getReference(CHAT_ROOT).child(chatRefKey);
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // todo 역시 데이터를 스냅샷으로 관리해야 되는데 할때마다 요청함..
                clearDataList();

                RemoteChatObj remoteChatObj = dataSnapshot.getValue(RemoteChatObj.class);
                if (remoteChatObj == null) {    // 새로 생성한 경우 chats가 존재하지 않는다...
                    return;
                }
                String chats = remoteChatObj.getChats();
                List<Chat> chatList = GsonConv.getInstance().fromJson(chats, new TypeToken<ArrayList<Chat>>(){}.getType());
                for (Chat chat : chatList) {
                    addData(chat);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }


    public boolean dummyUserToggle = true;
    public String dummyUserTypeTo = "me";
    public String dummyUserTypeFrom = "you";

    public void onSendBtn(){

        String currentMsg = inputEditText.getText().toString();

        if("".equals(currentMsg)){
            return;
        }

        Chat chat = null;
        if(dummyUserToggle){
            chat = new Chat(dummyUserTypeTo, currentMsg);
            dummyUserToggle = !dummyUserToggle;
        } else {
            chat = new Chat(dummyUserTypeFrom, currentMsg);
            dummyUserToggle = !dummyUserToggle;
        }

        // todo 추가된것에 대해서만 넣는게 더 좋은 설계일것 같은데... 계속 업데이트 하네... 채팅이 길어지면 용량이 커진다....
        // System Milli seconds...
        chatList.add(chat);
        String jsonChats = GsonConv.getInstance().toJson(chatList);
        Log.e(TAG, jsonChats);

        /* Send to firebase */
        chatRef.setValue(new RemoteChatObj("now", jsonChats));

        // new RemoteChatObj();

        /* Dummy Chat create.... */
        //Chat dummychat = new Chat();
        //dummychat.getChatPieceList().add(new Chat.ChatPiece());

        /*
        Chat chatObj = new Chat();
        // getCurrentChat
        String bbsKey = chatRef.push().getKey();
        chatRef.child(bbsKey).setValue(bbs); // <- 너 차일드의 bbsKey에 값을 넣겠다.
        */
    }

    public void onMorseBtn(){

    }

    @Override
    public CustomViewHolder throwCustomViewHolder(View view) {
        return new CustomViewHolder(view) {
            TextView textViewOwner;
            TextView textViewMsg;

            @Override
            public void updateRecyclerItemView(View view, Chat chat) {
                textViewMsg.setText(chat.getMessage());
                textViewOwner.setText(chat.getOwner());
            }

            @Override
            public void dependencyInjection(View itemView, Chat chat) {
                textViewOwner = (TextView) itemView.findViewById(R.id.item_textView_owner);
                textViewMsg = (TextView) itemView.findViewById(R.id.item_textView_msg);
            }

            @Override
            public void onClick(View v) {

            }
        };
    }

    @Override
    public int throwFragmentLayoutResId() {
        return R.layout.fragment_chat;
    }

    @Override
    public int throwRecyclerViewResId() {
        return R.id.chat_fragment_chats;
    }

    @Override
    public List<Chat> throwDataList() {
        return chatList;
    }

    @Override
    public int throwItemLayoutId() {
        return R.layout.item_chat_piece;
    }

    /* Dependecny Injection */
    TextView inputEditText;
    Button btnMorse;
    Button btnSend;

    private void dependencyInejctionAndAddListener(View view) {
        this.widgetsDependencyInejction(view);
        this.addWidgetsListener();
    }

    private void widgetsDependencyInejction(View view) {
        inputEditText = (TextView) view.findViewById(R.id.chat_fragment_editText_input);
        btnMorse = (Button) view.findViewById(R.id.chat_fragment_btn_morse);
        btnSend = (Button) view.findViewById(R.id.chat_fragment_btn_send);
    }

    private void addWidgetsListener() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendBtn();
            }
        });

        btnMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMorseBtn();
            }
        });
    }
}
