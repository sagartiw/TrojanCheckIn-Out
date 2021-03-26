package com.team13.trojancheckin_out.Layouts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<History> historyList;

    //getting the context and product list with constructor
    public HistoryAdapter(Context mCtx, List<History> historyList) {
        this.mCtx = mCtx;
        this.historyList = historyList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_history, null);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        //getting the product of the specified position
        History hist = historyList.get(position);

        //binding the data with the viewholder views
        holder.abbreviation.setText(hist.getAbbreviation());
        holder.action.setText(hist.getAction());
        holder.time.setText(hist.getTime());

    }


    @Override
    public int getItemCount() {
        if (historyList == null) return 0;
        if (historyList.isEmpty()) return 0;
        return historyList.size();
    }


    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView abbreviation, action, time;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            abbreviation = itemView.findViewById(R.id.fullName);
            action = itemView.findViewById(R.id.fullName2);
            time = itemView.findViewById(R.id.fullName3);
        }
    }

}
