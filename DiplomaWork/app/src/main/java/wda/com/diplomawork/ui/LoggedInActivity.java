package wda.com.diplomawork.ui;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import wda.com.diplomawork.R;
import wda.com.diplomawork.base.BaseActivity;
import wda.com.diplomawork.core.realM.User;
import wda.com.diplomawork.core.realM.UserRM;
import wda.com.diplomawork.core.usersRecycler.MyAdapter;
import wda.com.diplomawork.core.usersRecycler.RecyclerViewInterface;
import wda.com.diplomawork.util.Constant;

public class LoggedInActivity extends BaseActivity implements RecyclerViewInterface {

    private List<User> allUsers;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        viewConfigs();
        getAllUsersFromFirebaseDataBase();
    }

    private void viewConfigs() {
        findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUserActionPerformed();
            }
        });
        recyclerViewConfigs();
    }

    private void logoutUserActionPerformed() {
        UserRM.removeUserFromDb();
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    private void recyclerViewConfigs() {
        allUsers = new ArrayList<>();
        adapter = new MyAdapter(allUsers, this);
        RecyclerView usersListView = findViewById(R.id.user_list_view);
        usersListView.setHasFixedSize(true);
        usersListView.setLayoutManager(new LinearLayoutManager(this));
        usersListView.setAdapter(adapter);
    }

    public void getAllUsersFromFirebaseDataBase() {
        myRef = database.getReference().child("users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                convertHashMapToUsers(dataSnapshot.getValue());
                adapter.notifyDataSetChanged();
                if(allUsers.isEmpty()){
                    findViewById(R.id.no_active_user_text_view).setVisibility(View.VISIBLE);
                }
                findViewById(R.id.progress_layout).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("");
            }
        });
    }

    private void convertHashMapToUsers(Object value) {
        Set<String> keys = ((HashMap)value).keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ){
            String key = iterator.next();
            HashMap<String, String>  map = (HashMap<String, String>) ((HashMap)value).get(key);
            if(!map.get("login").equals(UserRM.getLoggedInUser().getLogin())){
                allUsers.add(new User(map.get("firstName"), map.get("lastName"), map.get("login"), map.get("lastSeen"), map.get("uId")));
            }
        }

    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(Constant.CHAT_WITH_USER, allUsers.get(position));
        startActivity(intent);
    }
}
