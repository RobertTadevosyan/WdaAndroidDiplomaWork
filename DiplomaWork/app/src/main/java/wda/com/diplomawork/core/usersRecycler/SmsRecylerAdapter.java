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
import wda.com.diplomawork.core.realM.Sms;
import wda.com.diplomawork.core.realM.UserRM;

public class SmsRecylerAdapter extends RecyclerView.Adapter<SmsRecylerAdapter.ViewHolder> {
    private List<Sms> smsList;
    private RecyclerViewInterface recyclerViewInterface;
    private final int TYPE_MINE = 0;
    private final int TYPE_NOT_MINE = 1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View root;
        public TextView mesageTextView;
        public TextView timeTextView;
        public int position;
        public ViewHolder(View root) {
            super(root);
            this.root = root;
            mesageTextView = root.findViewById(R.id.message_text_view);
            timeTextView = root.findViewById(R.id.time_text_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SmsRecylerAdapter(List<Sms> users, RecyclerViewInterface recyclerViewInterface) {
        this.smsList = users;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SmsRecylerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = null;
        if(viewType == TYPE_MINE){
            v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sms_item_send, parent, false);
        } else {
            v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sms_item_recieve, parent, false);
        }

        final ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface != null){
                    recyclerViewInterface.onItemClicked(vh.position);
                }
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Sms sms = smsList.get(position);
        holder.mesageTextView.setText(sms.getText());
        String timeText = new SimpleDateFormat("HH:mm").format(new Date(Long.valueOf(sms.getSendTime())));
        holder.timeTextView.setText(timeText);
        holder.position = position;

    }

    @Override
    public int getItemViewType(int position) {
       if(smsList.get(position).getSenderId().equals(UserRM.getLoggedInUser().getuId())){
           return TYPE_MINE;
       } else {
           return TYPE_NOT_MINE;
       }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return smsList.size();
    }
}