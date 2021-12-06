package com.example.studyplanner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private static final String TAG = "RecyclerAdapter";

    private ArrayList<String> mEvents;
    private ArrayList<String> mDates;
    private ArrayList<String> mTimes;
    private ArrayList<String> mDescriptions;
    private Context mContext;
    public Database DB;


    public RecyclerAdapter(Context context,
                           ArrayList<String> events,
                           ArrayList<String> dates,
                           ArrayList<String> times,
                           ArrayList<String> descriptions
                           )
    {
        mEvents = events;
        mDates = dates;
        mTimes = times;
        mDescriptions = descriptions;
        mContext = context;
        DB = new Database(mContext);

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent, false );
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBind called");
        int adapter_pos = holder.getAdapterPosition();

        holder.event_txt.setText("Event: "+mEvents.get(adapter_pos));
        holder.date_txt.setText("Date: "+mDates.get(adapter_pos));
        holder.time_txt.setText("Time: "+mTimes.get(adapter_pos));
        holder.description_txt.setText("Description: "+ mDescriptions.get(adapter_pos));

        holder.remove_event.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(mContext);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to delete this event"+"("+mEvents.get(position)+")"+"?");

                // Set Alert Title
                builder.setTitle("Alert!");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name
                // OnClickListener method is use of
                // DialogInterface interface.

                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        DB.deleteData(mEvents.get(position));

                                        mDates.remove(adapter_pos);
                                        mEvents.remove(adapter_pos);
                                        mDescriptions.remove(adapter_pos);
                                        mTimes.remove(adapter_pos);

                                        RecyclerAdapter.this.notifyItemRemoved(adapter_pos);

                                        Toast.makeText(mContext, "Event successfully deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.
                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView event_txt;
        TextView date_txt;
        TextView time_txt;
        TextView description_txt;
        ImageView remove_event;
        RelativeLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            event_txt = itemView.findViewById(R.id.event);
            date_txt = itemView.findViewById(R.id.date);
            time_txt = itemView.findViewById(R.id.time);
            description_txt = itemView.findViewById(R.id.description);
            remove_event = itemView.findViewById(R.id.remove_event);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
