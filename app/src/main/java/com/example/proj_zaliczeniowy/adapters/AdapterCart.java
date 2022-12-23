package com.example.proj_zaliczeniowy.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proj_zaliczeniowy.R;
import com.example.proj_zaliczeniowy.models.ProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {

    private List<ProductModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private SharedPreferences sharedPreferences;

    public AdapterCart(Context context, List<ProductModel> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.sharedPreferences = context.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_cart_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductModel productModel = mData.get(position);
        holder.imageView.setImageResource(productModel.getImagePath());
        holder.tvName.setText(productModel.getName());
        holder.tvPrice.setText(String.valueOf(productModel.getPrice()));
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mData.size());
                if (sharedPreferences.getString("ids", null) != null) {
                    String ids = sharedPreferences.getString("ids", null);
                    List<String> idList = new ArrayList<>(Arrays.asList(ids.split(" ")));
                    idList.removeAll(Arrays.asList("", " ", "  ", null));
                    List<String> idList2 = new ArrayList<>();
                    for (String id : idList) {
                        String temp = id.trim();
                        idList2.add(temp);
                    }
                    Log.v("TAG", idList2.toString());
                    idList2.remove(position);
                    String prefs = "";
                    for (String id : idList2) {
                        prefs += id + " ";
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("ids");
                    editor.putString("ids", prefs);
                    editor.apply();

                    int total = 0;
                    for (ProductModel pm : mData){
                        total += pm.getPrice();
                    }

                    TextView totalP =((Activity) holder.itemView.getContext()).findViewById(R.id.total_price);
                    totalP.setText(String.valueOf(total));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView tvName;
        TextView tvPrice;
        ImageButton imageButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cart_image);
            tvName = itemView.findViewById(R.id.cart_name);
            tvPrice = itemView.findViewById(R.id.cart_price);
            imageButton = itemView.findViewById(R.id.del_item);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null){
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
