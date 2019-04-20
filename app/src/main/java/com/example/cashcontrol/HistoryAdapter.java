package com.example.cashcontrol;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    //this context will be used to inflate the layout
    private Context mCtx;

    //we are storing all the history in a list
    private List<History> historyList;

    //getting the context and history list with constructor
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
        History history = historyList.get(position);
        //binding the data with the viewholder views
        holder.textViewMoney.setText(history.getMoney());
        holder.dt.setText(history.getTime());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMoney;
        TextView dt;


        public HistoryViewHolder(View itemView) {
            super(itemView);
            textViewMoney = itemView.findViewById(R.id.money_textview);
            dt  = itemView.findViewById(R.id.dt_textView);
        }
    }
}
