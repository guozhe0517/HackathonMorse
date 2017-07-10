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
import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseDao;
import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseHelper;
import com.doyoon.android.hackathonmorse.domain.firebase.value.Chat;
import com.doyoon.android.hackathonmorse.domain.firebase.value.UserChatroom;
import com.doyoon.android.hackathonmorse.presenter.fragment.abst.RecyclerFragment;
import com.doyoon.android.hackathonmorse.presenter.status.CurrentUser;
import com.doyoon.android.hackathonmorse.util.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.doyoon.android.hackathonmorse.domain.firebase.FirebaseHelper.getModelDir;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class ChatFragment extends RecyclerFragment<Chat> {
    public static String TAG = ChatFragment.class.getName();

    private String friendKey = null;
    private String chatroomKey = null;
    private List<Chat> chatList = new ArrayList<>();

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* Dependency Injection */
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.dependencyInejctionAndAddListener(view);

        // todo 채팅방 유저가 두명있어야 한다.from, to  채팅 기록이 list<String>으로 쌓이게 된다....
        // todo 문제는 리스너가 계속 달려 있으면 안될거 같은데.... Fragment는 계속해서 new 가 되므로...

        //todo null check
        Bundle bundle = getArguments();
        this.friendKey = bundle.getString(Const.FRIEND_KEY_IN_BUNDLE);
        this.chatroomKey = bundle.getString(Const.CHAT_KEY_IN_BUNDLE);

        Log.e(TAG, "넘겨받은 bundle의 값들은 friend key : " + this.friendKey + ", chatroom key : " + this.chatroomKey);
        // friendUid = bundle.getString(Const.FRIEND_KEY_IN_BUNDLE);
        // friendName = bundle.getString(Const.FRIEND_NAME_BUNDLE_KEY);

        if (this.chatroomKey != null) {
            addChatListener(chatroomKey);
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

        if (chatroomKey == null) {  // 새채팅을 시작합니다.
            Log.e(TAG, "새 채팅을 시작합니다.");
            String modelDir = getModelDir("userChatroom");
            String chatroomKey = FirebaseDatabase.getInstance().getReference(modelDir).push().getKey();
            this.chatroomKey = chatroomKey;
            addChatListener(chatroomKey);

            /* User chat room 추가 */
            UserChatroom userChatroom = new UserChatroom();
            userChatroom.setKey(chatroomKey);
            FirebaseDao.insert(userChatroom, CurrentUser.getUid());

            /* Friend에 기존 채팅방이 있다고 알려줄것....  */
            String friendModelDir = FirebaseHelper.getModelDir("friend", CurrentUser.getUid());
            FirebaseDatabase.getInstance().getReference(friendModelDir + this.friendKey).child("existChatKey").setValue(this.chatroomKey);

            /* 친구꺼에 채팅방 추가... */


        }
        Chat chat = new Chat(CurrentUser.getUid(), currentMsg);
        FirebaseDao.insert(chat, this.chatroomKey);
    }

    public void onMorseBtn(){

    }

    private void addChatListener(String chatroomKey){
        String modelDir = FirebaseHelper.getModelDir("chat", chatroomKey);
        FirebaseDatabase.getInstance().getReference(modelDir).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // todo 역시 데이터를 스냅샷으로 관리해야 되는데 할때마다 요청함..
                // todo 추가된것에 대해서만 넣는게 더 좋은 설계일것 같은데... 계속 업데이트 하네... 채팅이 길어지면 용량이 커진다....  // child added
                clearDataList();

                for(DataSnapshot item : dataSnapshot.getChildren()){
                    Chat chat = item.getValue(Chat.class);
                    chatList.add(chat);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
