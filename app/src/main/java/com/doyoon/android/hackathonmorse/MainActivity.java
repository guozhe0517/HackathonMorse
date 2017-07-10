package com.doyoon.android.hackathonmorse;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseDao;
import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseHelper;
import com.doyoon.android.hackathonmorse.presenter.fragment.FriendAndChatListFragment;
import com.doyoon.android.hackathonmorse.presenter.status.CurrentUser;
import com.doyoon.android.hackathonmorse.util.converter.ConvString;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get UID */
        Bundle bundle = getIntent().getExtras();    // todo 다시켜면 RemoteDao.MYUID의 값이 null 이네.... 서비스나 다른곳에 저장을 해둬야 하는구만
        String uid = "";
        if (bundle != null) {
            uid = bundle.getString("email");
        } else { // remove this code
            //uid = "miraee05@naver.com" + System.currentTimeMillis();
            uid = "d";
        }

        CurrentUser.setUid(ConvString.comma2string(uid));

        /* Load Firebase Database Structure */
        FirebaseHelper.loadDbStructure(getBaseContext());
        // FirebaseHelper.printAllModelKey();

        /* Search in DB, unless finish */
        this.startFragment(FriendAndChatListFragment.newInstance());

        // todo Listnenr 세부적으로 하위노드에서 컨트롤...
        String signupModelDir = FirebaseHelper.getModelDir("signup", CurrentUser.getUid());
        DatabaseReference signupRef = FirebaseDatabase.getInstance().getReference(signupModelDir + "signup");

        signupRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {   // 사용자가 없으면 새 사용자를 등록한다.
                    FirebaseDao.insert("signup", true, CurrentUser.getUid());
                    Log.e(TAG, "사용자를 새로 등록했습니다.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


    }

    public void startFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    public void goFragment(Fragment fragment) {
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goPrevFragment(){
        FragmentManager manager = this.getSupportFragmentManager();
        manager.popBackStack();
    }
}
