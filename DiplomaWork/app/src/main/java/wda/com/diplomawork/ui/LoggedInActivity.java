package wda.com.diplomawork.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

public class LoggedInActivity extends BaseActivity {

    private List<User> allUsers;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        recyclerViewConfigs();
        getAllUsersFromFirebaseDataBase();
    }

    private void recyclerViewConfigs() {
        allUsers = new ArrayList<>();
        adapter = new MyAdapter(allUsers);
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
//                HashMap<String, User> userMap = (HashMap<String, String>)dataSnapshot.getValue();
//                userFromFDB.setFirstName(userMap.get("firstName"));
//                userFromFDB.setLastName(userMap.get("lastName"));
//                userFromFDB.setLogin(userMap.get("login"));
                convertHashMapToUsers(dataSnapshot.getValue());
                adapter.notifyDataSetChanged();
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
            allUsers.add(new User(map.get("firstName"), map.get("lastName"), map.get("login")));
        }

    }
}
