package com.example.proj_zaliczeniowy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.proj_zaliczeniowy.others.DatabaseHelper;
import com.example.proj_zaliczeniowy.R;
import com.example.proj_zaliczeniowy.adapters.AdapterOrders;
import com.example.proj_zaliczeniowy.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";

    ImageButton ibBack;
    FragmentManager fragmentManager;
    RecyclerView rvOrders;
    List<OrderModel> recyclerData;
    DatabaseHelper databaseHelper;
    AdapterOrders adapterOrders;
    SharedPreferences sharedPreferences;
    String email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(EMAIL_KEY, null);

        fragmentManager = getParentFragmentManager();
        ibBack = view.findViewById(R.id.ib_arrow_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, new HomeScreenFragment())
                        .commit();
            }
        });

        rvOrders = view.findViewById(R.id.rv_orders);
        recyclerData = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getActivity());
        recyclerData.addAll(databaseHelper.selectAllOrders(new OrderModel(email)));
        adapterOrders = new AdapterOrders(getActivity(), recyclerData);
        rvOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOrders.setAdapter(adapterOrders);


        return view;
    }
}