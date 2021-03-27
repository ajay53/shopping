package com.example.shopping.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopping.R;
import com.example.shopping.broadcastReceiver.ConnectivityBroadcastReceiver;
import com.example.shopping.broadcastReceiver.MusicBroadcastReceiver;
import com.example.shopping.databinding.ActivityLoginBinding;
import com.example.shopping.model.User;
import com.example.shopping.utility.NotificationBuilder;
import com.example.shopping.utility.Util;
import com.example.shopping.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private LoginViewModel viewModel;
    ActivityLoginBinding binding;
    private User user;
    private FirebaseAuth mAuth;
    private final Activity activity = this;
    private final MusicBroadcastReceiver musicBroadcastReceiver = new MusicBroadcastReceiver();
    private final ConnectivityBroadcastReceiver connectivityBroadcastReceiver = new ConnectivityBroadcastReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
        askPermissions();
        setBroadcastReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(musicBroadcastReceiver);
//        unregisterReceiver(connectivityBroadcastReceiver);
    }

    private void init() {
        Log.d(TAG, "init: ");

        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        user = new User("", "", "", "", "");
        binding.setUser(user);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnTest = findViewById(R.id.btnTest);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnTest.setOnClickListener(this);
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            Intent i = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            startActivity(i);
        }
    }

    private void setBroadcastReceiver() {
        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");
        iF.addAction("com.android.music.musicservicecommand");
        iF.addAction("com.android.music.updateprogress");

        registerReceiver(musicBroadcastReceiver, iF);

        /*IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityBroadcastReceiver, filter);*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            String path = data.getDataString();
            assert path != null;
            byte[] bbb = path.getBytes();
            /*File file = new File(path);
            if(file.exists()){
                Log.d(TAG, "onClick: File Exists");
            }
            byte[] b = Files.readAllBytes(Paths.get(path));*/

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference mountainsRef = storageRef.child("sample.pdf");


            UploadTask uploadTask = mountainsRef.putBytes(bbb);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG, "onFailure: ");
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: ");
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });
            Log.d(TAG, "onActivityResult: ");

        } catch (Exception e) {
            e.printStackTrace();
        }
        // do somthing...

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        int id = v.getId();

        //collapse soft keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (id == R.id.btnLogin) {
            if (!user.getEmailId().isEmpty() && !user.getPassword().isEmpty()) {
                //firebase auth
                mAuth.signInWithEmailAndPassword(user.getEmailId(), user.getPassword())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                Intent intent = new Intent(activity, NavigationActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Util.showSnackBar(activity, "Authentication failed.");
                            }
                        });
                //setting preferences
                /*SharedPreferences.Editor editor = MainActivity.sharedPref.edit();
                editor.putString(Constant.USERNAME, user.getUsername());
                editor.putString(Constant.PASSWORD, user.getPassword());
                editor.apply();

                Intent intent = new Intent(this, NavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();*/
            } else {
                Util.showSnackBar(this, "Please fill all fields");
            }
        } else if (id == R.id.btnRegister) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnTest) {
            NotificationCompat.Builder builder = NotificationBuilder.testNotificationBuilder(activity, "test","test");
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());

            try {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                startActivityForResult(intent, 1);


                /*
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "sample.pdf";
                File file = new File(path);
                if(file.exists()){
                    Log.d(TAG, "onClick: File Exists");
                }
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference mountainsRef = storageRef.child("mountains.jpg");
                StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");
                mountainsRef.getName().equals(mountainImagesRef.getName());    // true
                mountainsRef.getPath().equals(mountainImagesRef.getPath());

                byte[] b = Files.readAllBytes(Paths.get(path));

                UploadTask uploadTask = mountainsRef.putBytes(b);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d(TAG, "onFailure: ");
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: ");
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                });
                Log.d(TAG, "onClick: Test");*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
