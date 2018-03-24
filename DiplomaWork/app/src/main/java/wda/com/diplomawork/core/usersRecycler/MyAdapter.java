package wda.com.diplomawork.core.usersRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import wda.com.diplomawork.R;
import wda.com.diplomawork.core.realM.User;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<User> users;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View root;
        public TextView nameTextView;
        public TextView emailTextView;
        public TextView lastSeenTextView;
        public ViewHolder(View root) {
            super(root);
            this.root = root;
            nameTextView = root.findViewById(R.id.name_text_view);
            emailTextView = root.findViewById(R.id.email_text_view);
            lastSeenTextView = root.findViewById(R.id.last_seen_text_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<User> users) {
        this.users = users;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        User user = users.get(position);
        holder.nameTextView.setText(user.getFirstName() + " "  + user.getLastName());
        holder.emailTextView.setText(user.getLogin());
        Long lastSeen = Long.valueOf(user.getLastSeen());
        holder.lastSeenTextView.setText("Last login : " + new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm").format(new Date(lastSeen)));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return users.size();
    }
}