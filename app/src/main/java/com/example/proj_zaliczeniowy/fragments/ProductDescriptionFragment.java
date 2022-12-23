package com.example.proj_zaliczeniowy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import com.example.proj_zaliczeniowy.others.DatabaseHelper;
import com.example.proj_zaliczeniowy.R;
import com.example.proj_zaliczeniowy.models.ProductModel;

import java.util.HashSet;
import java.util.Set;

public class ProductDescriptionFragment extends Fragment implements Serializable {

    TextView name;
    TextView price;
    TextView cat;
    TextView objClass;
    TextView mag;
    TextView temp;
    TextView mass;
    TextView desc;
    ProductModel product;

    ImageButton ibBack;
    ImageButton ibCart;
    ImageView productIMG;
    Button btnBuy;

    FragmentManager manager;
    SharedPreferences sharedPreferences;

    Set<String> orders;
    int quantity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);

        orders = new HashSet<>();

        ibBack = view.findViewById(R.id.back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = getParentFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, new HomeScreenFragment())
                        .commit();
            }
        });

        ibCart = view.findViewById(R.id.cart);
        ibCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = getParentFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, new CartFragment())
                        .commit();
            }
        });

        btnBuy = view.findViewById(R.id.buy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = getActivity().getSharedPreferences("shopping_cart",Context.MODE_PRIVATE);
                String itemsID = sharedPreferences.getString("ids", "0");
                if (itemsID == "0"){
                    itemsID = String.valueOf(product.getId());
                } else {
                    itemsID += " " + product.getId();
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ids", itemsID);
                editor.apply();


            }
        });

        name = view.findViewById(R.id.name_product);
        price = view.findViewById(R.id.price_product);
        cat = view.findViewById(R.id.category_product);
        objClass = view.findViewById(R.id.class_product);
        mag = view.findViewById(R.id.magnitude_product);
        temp = view.findViewById(R.id.temperature_product);
        mass = view.findViewById(R.id.mass_product);
        desc = view.findViewById(R.id.description_product);
        productIMG = view.findViewById(R.id.image_product);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int myInt = bundle.getInt("ProductID", 42);
            setup(myInt);
        }

        return view;
    }

    private void setup(int ID){
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        product = databaseHelper.selectOneProduct(ID).get(0);
        name.setText(product.getName());
        price.setText(product.getPrice() + "$");
        cat.setText("Category: " + product.getCategory());
        objClass.setText("Type: " + product.getObjClass());
        mag.setText("Magnitude: " + product.getMagnitude());
        temp.setText("Surface temperature: " + product.getTemperature());
        mass.setText("Mass: " + product.getMass());
        desc.setText(product.getDescription());
        productIMG.setImageResource(product.getImagePath());
    }
}