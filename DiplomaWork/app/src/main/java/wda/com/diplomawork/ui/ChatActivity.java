package wda.com.diplomawork.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wda.com.diplomawork.R;
import wda.com.diplomawork.base.BaseActivity;
import wda.com.diplomawork.core.realM.Chat;
import wda.com.diplomawork.core.realM.Sms;
import wda.com.diplomawork.core.realM.User;
import wda.com.diplomawork.core.realM.UserRM;
import wda.com.diplomawork.core.usersRecycler.SmsRecylerAdapter;
import wda.com.diplomawork.util.Constant;

public class ChatActivity extends BaseActivity {
    private RecyclerView smsRecylerView;
    private EditText messageEditText;
    private Button sendSmsButton;
    private Chat chat;
    private List<Sms> smsList = new ArrayList<>();
    private User withUser;
    private String chatId;
    private SmsRecylerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.CHAT_WITH_USER)) {
            withUser = (User) intent.getExtras().get(Constant.CHAT_WITH_USER);
        }
        checkFirebaseChilds();
        configureView();
    }

    private void checkFirebaseChilds() {
        myRef = database.getReference().child("chats");
        chatId = withUser.getuId() + " : " + UserRM.getLoggedInUser().getuId();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(chatId)){
                    chatId = UserRM.getLoggedInUser().getuId() + " : " + withUser.getuId();
                }
                firebaseDbConfigs();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void firebaseDbConfigs() {
        myRef = database.getReference().child("chats");
        myRef.child(chatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    smsList.clear();
                    ArrayList<HashMap<String, String>> arrayList = (ArrayList<HashMap<String, String>>) dataSnapshot.getValue();
                    for (int i = 0; i < arrayList.size(); i++) {
                        HashMap<String, String> sms = arrayList.get(i);
                        smsList.add(new Sms(sms.get("text"), sms.get("senderId"), sms.get("sendTime")));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ChatActivity.this, "No messages", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println();
            }
        });
    }

    private void configureView() {
        messageEditText = findViewById(R.id.send_sms_edit_text);
        sendSmsButton = findViewById(R.id.send_sms_button);
        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!messageEditText.getText().toString().isEmpty()) {
                    smsList.add(new Sms(messageEditText.getText().toString(), UserRM.getLoggedInUser().getuId(), String.valueOf(System.currentTimeMillis())));
                    messageEditText.setText("");
                    myRef = database.getReference().getRoot();
                    myRef.child("chats").child(chatId).setValue(smsList);
                }
            }
        });
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        smsRecylerView = findViewById(R.id.sms_recycler_view);
        smsRecylerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SmsRecylerAdapter(smsList, null);
        smsRecylerView.setLayoutManager(new LinearLayoutManager(this));
        smsRecylerView.setAdapter(adapter);
    }
}
