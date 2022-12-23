package com.example.proj_zaliczeniowy.adapters;

import static com.example.proj_zaliczeniowy.R.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proj_zaliczeniowy.models.OrderModel;

import java.util.List;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.ViewHolder> {

    private List<OrderModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public AdapterOrders(Context context, List<OrderModel> mData) {
        this.mData = mData;
        this.mInflater =  LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layout.custom_order_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel orderModel = mData.get(position);

        holder.tvContents.setText(orderModel.getRecipient());
        holder.tvDate.setText(orderModel.getContent());
        holder.tvPrice.setText(String.valueOf(orderModel.getTotalPrice()));
        holder.ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mInflater.getContext(), string.merry_christmas, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvContents;
        TextView tvDate;
        TextView tvPrice;
        ImageButton ibShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvContents = itemView.findViewById(id.order_contents);
            tvDate = itemView.findViewById(id.order_date);
            tvPrice = itemView.findViewById(id.order_price);
            ibShare = itemView.findViewById(id.order_share);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        }

    }

    void setmItemClickListener(ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

}
