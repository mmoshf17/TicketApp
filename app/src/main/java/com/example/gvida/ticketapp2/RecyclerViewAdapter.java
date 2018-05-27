package com.example.gvida.ticketapp2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gvida on 27/05/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{

    private Context mContext;
    private List<Tickets> mData;

    public RecyclerViewAdapter(Context mContext, List<Tickets> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.custom_list_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_date.setText(mData.get(position).getStartingDate());
        holder.tv_category.setText(mData.get(position).getCategory());
        holder.tv_price.setText(mData.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_date, tv_category, tv_price;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name_of_the_ticket);
            tv_date = itemView.findViewById(R.id.date_of_the_ticket);
            tv_category = itemView.findViewById(R.id.category_of_the_ticket);
            tv_price = itemView.findViewById(R.id.price_of_the_ticket);
        }
    }
}
