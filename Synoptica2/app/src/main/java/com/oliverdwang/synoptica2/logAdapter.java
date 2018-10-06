package com.oliverdwang.synoptica2;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class logAdapter extends RecyclerView.Adapter<logAdapter.logViewHolder> {

    private List<logEntry> logList;

    public class logViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView prompt;
        public ImageView mood;

        public logViewHolder(View view) {
            super(view);

            date = view.findViewById(R.id.textView_date);
            prompt = view.findViewById(R.id.textView_prompt);
            mood = view.findViewById(R.id.imageView_mood);
        }
    }

    public logAdapter(List<logEntry> logList) {
        this.logList = logList;
    }

    public logViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_entry, parent, false);


        return new logViewHolder(itemView);
    }

    public void onBindViewHolder(logViewHolder holder, int position) {
        logEntry logEntry = logList.get(position);
        holder.date.setText("Date: " + logEntry.getDayOfWeek() + ", " + logEntry.getMonth() + "/" + logEntry.getDay() + "/" + logEntry.getYear());
        holder.prompt.setText("Prompt: " + logEntry.getPrompt());
        switch(logEntry.getMood()) {
            case 0:
                holder.mood.setImageResource(R.drawable.ic_action_horrible);
                break;
            case 1:
                holder.mood.setImageResource(R.drawable.ic_action_bad);
                break;
            case 2:
                holder.mood.setImageResource(R.drawable.ic_action_neutral);
                break;
            case 3:
                holder.mood.setImageResource(R.drawable.ic_action_good);
                break;
            case 4:
                holder.mood.setImageResource(R.drawable.ic_action_great);
                break;
        }
    }

    public int getItemCount() {
        return logList.size();
    }

}
