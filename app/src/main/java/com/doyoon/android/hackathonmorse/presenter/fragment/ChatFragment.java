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
import com.doyoon.android.hackathonmorse.domain.firebase.value.Chat;
import com.doyoon.android.hackathonmorse.domain.RemoteChatObj;
import com.doyoon.android.hackathonmorse.domain.dao.RemoteDao;
import com.doyoon.android.hackathonmorse.domain.firebase.value.ChatKey;
import com.doyoon.android.hackathonmorse.presenter.fragment.abst.RecyclerFragment;
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

/**
 * Created by DOYOON on 7/6/2017.
 */

public class ChatFragment extends RecyclerFragment<Chat> {
    public static String TAG = ChatFragment.class.getName();

    private String chatRefKey = "";
    private String friendUid = "";
    private String friendName = "";
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

        // todo
        // 채팅방 유저가 두명있어야 한다.
        // from, to
        // 채팅 기록이 list<String>으로 쌓이게 된다....

        // todo 문제는 리스너가 계속 달려 있으면 안될거 같은데.... Fragment는 계속해서 new 가 되므로...

        //todo null check
        Bundle bundle = getArguments();
        chatRefKey = bundle.getString(Const.CHAT_BUNDLE_KEY);
        friendUid = bundle.getString(Const.FRIEND_UID_BUNDLE_KEY);
        friendName = bundle.getString(Const.FRIEND_NAME_BUNDLE_KEY);


        if(!Const.EMPTY_CHAT_KEY.equals(chatRefKey)) {
            Log.e(TAG, "Chat Ref KEy가 ''가 아닙니다. 기존 채팅 데이터를 불러옵니다");
            chatRef = FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_CHAT_ROOT).child(chatRefKey);
            chatRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // todo 역시 데이터를 스냅샷으로 관리해야 되는데 할때마다 요청함..
                    // todo 추가된것에 대해서만 넣는게 더 좋은 설계일것 같은데... 계속 업데이트 하네... 채팅이 길어지면 용량이 커진다....
                    clearDataList();

                    RemoteChatObj remoteChatObj = dataSnapshot.getValue(RemoteChatObj.class);
                    if (remoteChatObj == null) {    // 새로 생성한 경우 chats가 존재하지 않는다...
                        return;
                    }
                    String chats = remoteChatObj.getChats();
                    List<Chat> chatList = GsonConv.getInstance().fromJson(chats, new TypeToken<ArrayList<Chat>>() {
                    }.getType());
                    for (Chat chat : chatList) {
                        addData(chat);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

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

        /* If doesn't have chat refkey it's new chat */
        if(Const.EMPTY_CHAT_KEY.equals(chatRefKey)){
            Log.e(TAG, "새 채팅을 시작합니다.");
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_CHAT_ROOT);
            chatRefKey = rootRef.push().getKey();
            rootRef.push().getRef();
            chatRef = FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_CHAT_ROOT).child(chatRefKey);
            // todo 새로운 채팅일 경우 chatREf에만 추가하는 것이 아니라
            //jsonChatKeyList와 jsonFriendKeyList에도 추가해줘야 한다...

            /* 내꺼 friendKeyNode에 채팅방 uid에 추가 */
            ChatKey chatKey = new ChatKey(chatRefKey, "", friendName, friendUid, "now");
            FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_CHAT_ROOT).child(RemoteDao.MYUID).child(Const.CHAT_KEY_REF).child(chatRefKey).setValue(chatKey);

            /* 내꺼 friend Key Node 추가 */
            FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_USER_ROOT).child(RemoteDao.MYUID).child(Const.FRIEND_KEY_REF).child(friendUid).child("existChatRefKey").setValue(chatRefKey);

            /* 친구 Friend Key node 추가*/

            // friend 이름 내이름이랑 변경
            // FirebaseDatabase.getInstance().getReference(Const.FIRE_BASE_USER_ROOT).child(friendUid).child(Const.FRIEND_KEY_REF).child(friendUid).child("existChatRefKey").setValue(chatRefKey);



            // 친구 ChatKeyNode에 추가

            // 챗키 노드는 채팅 리사이클러를 열고 클릭하면 채팅

            chatRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // todo 역시 데이터를 스냅샷으로 관리해야 되는데 할때마다 요청함..
                    // todo 추가된것에 대해서만 넣는게 더 좋은 설계일것 같은데... 계속 업데이트 하네... 채팅이 길어지면 용량이 커진다....
                    clearDataList();

                    RemoteChatObj remoteChatObj = dataSnapshot.getValue(RemoteChatObj.class);
                    if (remoteChatObj == null) {    // 새로 생성한 경우 chats가 존재하지 않는다...
                        return;
                    }
                    String chats = remoteChatObj.getChats();
                    List<Chat> chatList = GsonConv.getInstance().fromJson(chats, new TypeToken<ArrayList<Chat>>() {
                    }.getType());
                    for (Chat chat : chatList) {
                        addData(chat);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        Chat chat = null;
        if(dummyUserToggle){
            chat = new Chat(friendUid, currentMsg);
            dummyUserToggle = !dummyUserToggle;
        } else {
            chat = new Chat(RemoteDao.MYUID, currentMsg);
            dummyUserToggle = !dummyUserToggle;
        }

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

    private void widgetsDependencyInejction(View view) {inputEditText = (TextView) view.findViewById(R.id.chat_fragment_editText_input);
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
