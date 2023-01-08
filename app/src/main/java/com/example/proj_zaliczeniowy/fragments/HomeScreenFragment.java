package com.example.proj_zaliczeniowy.fragments;

import static com.example.proj_zaliczeniowy.R.string.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proj_zaliczeniowy.others.DatabaseHelper;
import com.example.proj_zaliczeniowy.R;
import com.example.proj_zaliczeniowy.adapters.AdapterProducts;
import com.example.proj_zaliczeniowy.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenFragment extends Fragment{

    ImageButton imgBtnSettings;
    ImageButton ibCart;
    ImageView productImage;
    FragmentManager manager;

    RadioGroup radioGroup;
    RadioButton radioAll;
    AdapterProducts adapterProducts;
    GridLayoutManager layoutManager;

    androidx.appcompat.widget.SearchView searchView;

    private RecyclerView rvProducts;
    private List<ProductModel> recyclerDataArrayList;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;
    String email;
    TextView welcoming;

    DatabaseHelper databaseHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_screen, container, false);

        welcoming = view.findViewById(R.id.welcoming);


        manager = getParentFragmentManager();

        imgBtnSettings = view.findViewById(R.id.settings);
        imgBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), imgBtnSettings);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.orders:
//                                Toast.makeText(getActivity(), orders, Toast.LENGTH_SHORT).show();
                                manager.beginTransaction()
                                        .replace(R.id.fragmentContainerViewMain, new OrdersFragment())
                                        .commit();
                                break;

                            case R.id.about:
//                                Toast.makeText(getActivity(), about, Toast.LENGTH_SHORT).show();
                                manager.beginTransaction()
                                        .replace(R.id.fragmentContainerViewMain, new AboutFragment())
                                        .commit();
                                break;

                            case R.id.logOut:
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.apply();

                                manager.beginTransaction()
                                        .replace(R.id.fragmentContainerViewMain, new WelcomeFragment())
                                        .commit();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        searchView = view.findViewById(R.id.search_view_bar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedpreferences.getString(EMAIL_KEY, null);
        rvProducts = view.findViewById(R.id.rv_products);
        databaseHelper = new DatabaseHelper(getActivity());
        addProducts();
        recyclerDataArrayList = databaseHelper.selectAllProducts();
        String username = email.split("@")[0];
        String phrase = welcoming.getText().toString();
        welcoming.setText(phrase + username.toUpperCase());

        radioAll = view.findViewById(R.id.filter_all);
        radioAll.setChecked(true);
        radioGroup = view.findViewById(R.id.filters);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.filter_all:
                        recyclerDataArrayList = databaseHelper.selectAllProducts();
                        recycling();
                        break;

                    case R.id.filter_star:
                        recyclerDataArrayList = databaseHelper.selectStars();
                        recycling();
                        break;

                    case R.id.filter_exoplanet:
                        recyclerDataArrayList = databaseHelper.selectExoplanets();
                        recycling();
                        break;

                    case R.id.filter_satellite:
                        recyclerDataArrayList = databaseHelper.selectSatellites();
                        recycling();
                        break;

                    case R.id.filter_asteroid:
                        recyclerDataArrayList = databaseHelper.selectAsteroids();
                        recycling();
                        break;
                }
            }
        });

        adapterProducts = new AdapterProducts(new ArrayList<>(recyclerDataArrayList), getActivity());
        layoutManager = new GridLayoutManager(getActivity(), 2);

        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setAdapter(adapterProducts);

        ibCart = view.findViewById(R.id.cart);
        ibCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, new CartFragment())
                        .commit();
            }
        });


        return view;
    }

    private void addProducts(){
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        List<ProductModel> productModels = databaseHelper.selectAllProducts();
        if (productModels.size() == 0){
            databaseHelper.addProduct(new ProductModel("Betelgeuse", "star", "Red supergiant", (float) 0.58, 3500, 1000000, "Betelgeuse is a red supergiant of spectral type M1-2 and one of the largest stars visible to the naked eye. It is usually the tenth-brightest star in the night sky and, after Rigel, the second-brightest in the constellation of Orion. It is a distinctly reddish, semiregular variable star whose apparent magnitude, varying between +0.0 and +1.6, has the widest range displayed by any first-magnitude star. At near-infrared wavelengths, Betelgeuse is the brightest star in the night sky. Its Bayer designation is α Orionis, Latinised to Alpha Orionis and abbreviated Alpha Ori or α Ori.", R.drawable.betelgeuse, 440000));
            databaseHelper.addProduct(new ProductModel("Arcturus", "star", "red gigant", (float) -0.04, 4286, 1000000, "Arcturus is the brightest star in the northern constellation of Boötes. With an apparent visual magnitude of −0.05,[2] it is the third-brightest of the individual stars in the night sky, and the brightest in the northern celestial hemisphere. The name Arcturus originated from ancient Greece; it was then cataloged as α Boötis by Johann Bayer in 1603, which is Latinized to Alpha Boötis. Arcturus forms one corner of the Spring Triangle asterism.", R.drawable.arctutus, 420000));
            databaseHelper.addProduct(new ProductModel("Ganymede", "satellite", "red gigant", (float) 4.38, 110, 1000, "Ganymede, a satellite of Jupiter (Jupiter III), is the largest and most massive of the Solar System's moons. The ninth-largest object (including the Sun) of the Solar System, it is the largest without a substantial atmosphere (albeit not the most massive one, which is Mercury). It has a diameter of 5,268 km (3,273 mi), making it 26 percent larger than the planet Mercury by volume, although it is only 45 percent as massive.", R.drawable.ganymede, 420));
            databaseHelper.addProduct(new ProductModel("Proxima Centauri b", "exoplanet", "exoplanet", (float) 1.33, 234, 10000, "Alpha Centauri (Latinized from α Centauri and often abbreviated Alpha Cen or α Cen) is a triple star system in the constellation of Centaurus. It consists of 3 stars: Alpha Centauri A (officially Rigil Kentaurus),[12] Alpha Centauri B (officially Toliman)[12] and Alpha Centauri C (officially Proxima Centauri).[12] Proxima Centauri is also the closest star to the Sun at 4.2465 light-years (1.3020 pc).", R.drawable.proxima, 4200));
            databaseHelper.addProduct(new ProductModel("Ceres", "asteroid", "dwarf planet", (float) 3.34, 110, 100, "Ceres (/ˈsɪəriːz/;[18] minor-planet designation: 1 Ceres) is a dwarf planet in the asteroid belt between the orbits of Mars and Jupiter. It was the first asteroid discovered, on 1 January 1801, by Giuseppe Piazzi at Palermo Astronomical Observatory in Sicily and announced as a new planet. Ceres was later classified as an asteroid and then a dwarf planet – the only one always inside Neptune's orbit.", R.drawable.ceres, 42));
            databaseHelper.addProduct(new ProductModel("433 Eros", "asteroid", "cosmic stone", (float) 11.16, 373, 10, "Eros (minor planet designation: (433) Eros), provisional designation 1898 DQ, is a stony asteroid of the Amor group and the first discovered and second-largest near-Earth object with an elongated shape and a mean diameter of approximately 16.8 kilometers (10.4 miles). Visited by the NEAR Shoemaker space probe in 1998, it became the first asteroid ever studied from orbit around the asteroid.", R.drawable.eros, 42));
        }
    }

    private void recycling(){
        adapterProducts = new AdapterProducts(new ArrayList<>(recyclerDataArrayList), getActivity());
        layoutManager = new GridLayoutManager(getActivity(), 2);

        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setAdapter(adapterProducts);
    }


    private void filterList (String text){
        ArrayList<ProductModel> filteredList = new ArrayList<>();
        for (ProductModel item: recyclerDataArrayList){
            if (item.getName().toLowerCase().trim().contains(text.toLowerCase().trim())){
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()){
        } else {
            adapterProducts.setFilteredList(filteredList);
        }
    }

}
