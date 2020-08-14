package com.kim9212.ex76request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<BoardItem> items;

    public MyAdapter(Context context, ArrayList<BoardItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.boardlist_item, parent, false);
        VH holder= new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh= (VH)holder;

        BoardItem item= items.get(position);
        vh.tvNo.setText(item.no);
        vh.tvName.setText(item.name);
        vh.tvMsg.setText(item.message);
        vh.tvDate.setText(item.date);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvNo;
        TextView tvName;
        TextView tvMsg;
        TextView tvDate;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvNo= itemView.findViewById(R.id.tv_no);
            tvName= itemView.findViewById(R.id.tv_name);
            tvMsg= itemView.findViewById(R.id.tv_msg);
            tvDate= itemView.findViewById(R.id.tv_date);
        }
    }


}
