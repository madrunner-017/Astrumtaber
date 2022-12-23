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

import com.example.proj_zaliczeniowy.R;
import com.example.proj_zaliczeniowy.fragments.HomeScreenFragment;
import com.example.proj_zaliczeniowy.fragments.LogInFragment;
import com.example.proj_zaliczeniowy.fragments.SignUpFragment;


public class WelcomeFragment extends Fragment {

    Button btn_loginl;
    Button btn_signup;
    FragmentManager manager;
    SharedPreferences sharedPreferences;
    String email, password;

    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";


    @Override
    public void onStart() {
        super.onStart();
        if (email != null && password != null) {
            manager.beginTransaction()
                    .replace(R.id.fragmentContainerViewMain, new HomeScreenFragment())
                    .commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);


        manager = getParentFragmentManager();

        sharedPreferences= getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        email = sharedPreferences.getString(EMAIL_KEY, null);
        password = sharedPreferences.getString(PASSWORD_KEY, null);

        btn_loginl = (Button) view.findViewById(R.id.btn_login);
        btn_signup = (Button) view.findViewById(R.id.btn_signup);

        manager = getParentFragmentManager();

        btn_loginl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, new LogInFragment())
                        .commit();
            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, new SignUpFragment())
                        .commit();


            }
        });

        return view;
    }



}