package com.example.proj_zaliczeniowy.fragments;

import static com.example.proj_zaliczeniowy.R.string.log_failed;
import static com.example.proj_zaliczeniowy.R.string.logging;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proj_zaliczeniowy.others.DatabaseHelper;
import com.example.proj_zaliczeniowy.R;
import com.example.proj_zaliczeniowy.models.UserModel;


public class LogInFragment extends Fragment {

    ImageView logoBtn;
    EditText editEmail;
    EditText editPassword;
    Button logIn;
    boolean isValid;
    FragmentManager manager;
    DatabaseHelper databaseHelper;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String email, password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        logoBtn = view.findViewById(R.id.logoBtn2);
        editEmail = view.findViewById(R.id.edit_login_mail);
        editPassword = view.findViewById(R.id.edit_login_password);
        logIn = view.findViewById(R.id.btn_log);
        isValid = false;
        manager = getParentFragmentManager();
        databaseHelper = new DatabaseHelper(getActivity());

        logoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, new WelcomeFragment())
                        .commit();
            }
        });

        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedpreferences.getString(EMAIL_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel;

                isValid = validSignUp();
                if (isValid){
                    userModel = new UserModel(-1, editEmail.getText().toString(), editPassword.getText().toString());
                    boolean success = databaseHelper.selectOne(userModel.getEmail(), userModel.getPassword());
                    if (success) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString(EMAIL_KEY, editEmail.getText().toString());
                        editor.putString(PASSWORD_KEY, editPassword.getText().toString());
                        editor.commit();

                        Toast.makeText(getActivity(), logging, Toast.LENGTH_SHORT).show();
                        manager.beginTransaction()
                                .replace(R.id.fragmentContainerViewMain, new HomeScreenFragment())
                                .commit();
                    } else {
                        Toast.makeText(getActivity(), log_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        return view;
    }

    private boolean validSignUp() {
        if (editEmail.length() == 0){
            editEmail.setError("This field is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()){
            editEmail.setError("Its not an email address");
            return false;
        }
        if (editPassword.length() == 0){
            editPassword.setError("This field is required");
            return false;
        } else if (editPassword.length() < 8){
            editPassword.setError("Password must be minimum 8 chatacters long");
            return false;
        }
        return true;
    }
}