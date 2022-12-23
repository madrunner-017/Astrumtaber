package com.example.proj_zaliczeniowy.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proj_zaliczeniowy.MainActivity;
import com.example.proj_zaliczeniowy.fragments.ProductDescriptionFragment;
import com.example.proj_zaliczeniowy.R;
import com.example.proj_zaliczeniowy.models.ProductModel;

import java.util.ArrayList;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder>{

    private ArrayList<ProductModel> dataArrayList;
    private ArrayList<ProductModel> dataArrayListFull;
    private Context context;

    public AdapterProducts(ArrayList<ProductModel> recyclerDataArrayList, Context context){
        this.dataArrayList = recyclerDataArrayList;
        dataArrayListFull = new ArrayList<>(dataArrayList);
        this.context = context;
    }

    public void setFilteredList(ArrayList<ProductModel> filteredList){
        this.dataArrayList = filteredList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel productModel = dataArrayList.get(position);
        holder.tvProductName.setText(productModel.getName());
        holder.ivProduct.setImageResource(productModel.getImagePath());
        holder.tvPrice.setText(String.valueOf(productModel.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ADAPTER", dataArrayList.get(holder.getAdapterPosition()).toString());
                Toast.makeText(context, dataArrayList.get(holder.getAdapterPosition()).toString(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("ProductID", dataArrayList.get(holder.getAdapterPosition()).getId());
                ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment();
                productDescriptionFragment.setArguments(bundle);

                FragmentTransaction transaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                transaction
                        .replace(R.id.fragmentContainerViewMain,  productDescriptionFragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProduct;
        private TextView tvProductName;
        private TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.product_image);
            tvProductName = itemView.findViewById(R.id.product_name);
            tvPrice = itemView.findViewById(R.id.product_cost);

        }

    }
}
