package com.example.proj_zaliczeniowy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proj_zaliczeniowy.others.DatabaseHelper;
import com.example.proj_zaliczeniowy.others.JavaMailAPI;
import com.example.proj_zaliczeniowy.R;
import com.example.proj_zaliczeniowy.adapters.AdapterCart;
import com.example.proj_zaliczeniowy.models.OrderModel;
import com.example.proj_zaliczeniowy.models.ProductModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartFragment extends Fragment {

    AdapterCart adapterCart;
    RecyclerView rvCart;
    List<ProductModel> recyclerData;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProductModel productModel;
    DatabaseHelper databaseHelper;

    TextView tvTotalPrice;
    ImageButton ibBack;
    ImageButton ibDelete;
    Button btnPlaceOrder;

    FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rvCart = view.findViewById(R.id.rv_my_cart);

        recyclerData = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("ids",null) != null){
            String ids = sharedPreferences.getString("ids", null);
            List<String> idList = new ArrayList<>(Arrays.asList(ids.split(" ")));
            idList.removeAll(Arrays.asList("", " ", null));
            List<String> idList2 = new ArrayList<>();
            for (String id : idList) {
                String temp = id.trim();
                idList2.add(temp);
            }
            Log.v("TAG", idList2.toString());

            for (String id: idList2){
                Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
                if (id == "" || id == " " || id == "  "){
                    continue;
                }else {
                    recyclerData.add(databaseHelper.selectOneProduct(Integer.valueOf(id)).get(0));
                    adapterCart = new AdapterCart(getActivity(), recyclerData);
                    rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvCart.setAdapter(adapterCart);
                }

            }
        }
            tvTotalPrice = view.findViewById(R.id.total_price);
            tvTotalPrice.setText(String.valueOf(totalPrice(recyclerData)));




        ibBack = view.findViewById(R.id.arrow_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = getParentFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, new HomeScreenFragment())
                        .commit();
            }
        });

        ibDelete = view.findViewById(R.id.delete_cart);
        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerData.clear();
                adapterCart = new AdapterCart(getActivity(), recyclerData);
                rvCart.setAdapter(adapterCart);
                editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
        });

        btnPlaceOrder = view.findViewById(R.id.place_order);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                databaseHelper = new DatabaseHelper(getActivity());
                String recipient = sharedPreferences.getString("email_key", null);
                String pass = sharedPreferences.getString("password_key", null);
                int phone = databaseHelper.selectPhoneNumber(recipient, pass);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String date = dtf.format(now);
                String content = "";
                for (ProductModel pm : recyclerData){
                    content += pm.getName() + " ";
                }
                OrderModel orderModel = new OrderModel(recipient, date, Integer.valueOf(tvTotalPrice.getText().toString()), content.trim());
                databaseHelper.addOrder(orderModel);
                Toast.makeText(getActivity(), orderModel.toString(), Toast.LENGTH_SHORT).show();
                manager = getParentFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, new OrdersFragment())
                        .commit();
                sharedPreferences = getActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();


                JavaMailAPI javaMailAPI = new JavaMailAPI(getActivity(), "wiktortorun@gmail.com", "your order", "You just ordered: "+ content.trim());
                javaMailAPI.execute();



                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(String.valueOf(phone), null, "You just ordered: "+ content.trim(), null, null);

            }
        });


        return view;
    }

    public int totalPrice(List<ProductModel> recyclerData){
        int total = 0;
        for (ProductModel product: recyclerData){
            total += product.getPrice();
        }
        return total;
    }


}