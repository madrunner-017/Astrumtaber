package com.example.proj_zaliczeniowy.fragments;

import static com.example.proj_zaliczeniowy.R.string.reg_succ;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.proj_zaliczeniowy.others.DatabaseHelper;
import com.example.proj_zaliczeniowy.R;
import com.example.proj_zaliczeniowy.models.UserModel;

public class SignUpFragment extends Fragment {

    ImageView logoBtn;
    LinearLayout inputs;
    EditText editEmail;
    EditText editPhone;
    EditText editPassword;
    EditText editPassword2;
    Button signIn;
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        logoBtn = view.findViewById(R.id.logoBtn);
        inputs = view.findViewById(R.id.signUpInputs);
        editEmail = view.findViewById(R.id.edit_signup_mail);
        editPhone = view.findViewById(R.id.edit_signup_phone);
        editPassword = view.findViewById(R.id.edit_signup_password);
        editPassword2 = view.findViewById(R.id.edit_signup_password2);
        signIn = view.findViewById(R.id.btn_sign);
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

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel;

                isValid = validSignUp();
                if (isValid){

                    userModel = new UserModel(-1, editEmail.getText().toString(), Integer.valueOf(editPhone.getText().toString()), editPassword.getText().toString());
                    if (!databaseHelper.selectOneMail(userModel.getEmail())){
                        Toast.makeText(getActivity(), userModel.toString(), Toast.LENGTH_SHORT).show();
                        boolean success = databaseHelper.addOne(userModel);
                        if (success){
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString(EMAIL_KEY, editEmail.getText().toString());
                            editor.putString(PASSWORD_KEY, editPassword.getText().toString());
                            editor.commit();

                            Toast.makeText(getActivity(), reg_succ, Toast.LENGTH_SHORT).show();
                            manager.beginTransaction()
                                    .replace(R.id.fragmentContainerViewMain, new HomeScreenFragment())
                                    .commit();
                        } else {
                            Toast.makeText(getActivity(), R.string.reg_unsucc, Toast.LENGTH_SHORT).show();
                        }  
                    } else {
                        Toast.makeText(getActivity(), "Account already exists", Toast.LENGTH_SHORT).show();
                    }
//                 


                }
            }
        });


//        setOnBackPressed();

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
        if (editPhone.length() == 0){
            editPhone.setError("This field is required");
            return false;
        } else if (!Patterns.PHONE.matcher(editPhone.getText().toString()).matches()){
            editPhone.setError("Its not a phone number");
            return false;
        }
        if (editPassword.length() == 0){
            editPassword.setError("This field is required");
            return false;
        } else if (editPassword.length() < 8){
            editPassword.setError("Password must be minimum 8 chatacters long");
            return false;
        }
        if (editPassword2.length() == 0){
            editPassword2.setError("This field is required");
            return false;
        }else if (!editPassword.getText().toString().equals(editPassword2.getText().toString())){
            editPassword2.setError("Must be same as the first one");
            return false;
        }

        return true;
    }

    private void setOnBackPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isEnabled()) {
                    FragmentManager manager = getParentFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.fragmentContainerViewMain, new WelcomeFragment())
                            .commit();
                    setEnabled(false);
                    requireActivity().onBackPressed();

                }
            }
        });
    }
}