package com.example.shopping.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.shopping.R;
import com.example.shopping.databinding.ActivityLoginBinding;
import com.example.shopping.databinding.ActivityRegistrationBinding;
import com.example.shopping.model.User;
import com.example.shopping.utility.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegistrationActivity";

    private ActivityRegistrationBinding binding;
    private FirebaseAuth mAuth;
    private User user;
    private final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        user = new User("", "", "", "", "");
        binding.setUser(user);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    private boolean validateFields() {
        return !user.getUsername().isEmpty() && !user.getPassword().isEmpty() && !user.getEmailId().isEmpty() && !user.getPhoneNo().isEmpty() && !user.getAddress().isEmpty();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnRegister) {
            if (validateFields()) {
                //persist in firebase
                mAuth.createUserWithEmailAndPassword(user.getEmailId(), user.getPassword())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Util.showSnackBar(activity, "Registration successful");
                                    //insert user in firebase realtime database
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("users");
                                    myRef.child(user.getPhoneNo()).setValue(user);

                                    // Registration success, navigate to Login Page
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(activity, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Util.showSnackBar(activity, "Registration failed");
                                }
                            }
                        });
//                Util.showSnackBar(this, "Registration successful");
            } else {
                Util.showSnackBar(this, "Please fill all fields");
            }
        }
    }
}