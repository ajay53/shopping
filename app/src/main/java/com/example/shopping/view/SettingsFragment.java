package com.example.shopping.view;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.shopping.R;
import com.example.shopping.listener.PictureCapturingListener;
import com.example.shopping.service.ACameraService;
import com.example.shopping.service.CameraService;
import com.example.shopping.utility.Constant;
import com.example.shopping.utility.Util;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SettingsFragment extends Fragment implements PictureCapturingListener, ActivityCompat.OnRequestPermissionsResultCallback {

    //variables
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    FragmentActivity activity;
    Context context;
    private static final String[] requiredPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_CODE = 1;
    private ACameraService pictureService;

    //widgets
    SwitchMaterial sw_capture_image;
    AppCompatImageView img_front;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        activity = requireActivity();
        context = getContext();
        checkPermissions();

        sw_capture_image = root.findViewById(R.id.sw_capture_image);
        img_front = root.findViewById(R.id.img_front);

        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        pictureService = CameraService.getInstance(activity);
        sw_capture_image.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showSnackBar("Starting capture!");
                    pictureService.startCapturing(SettingsFragment.this);
                    editor.putString(Constant.CAPTURE_IMAGE, Constant.YES);
                } else {
                    editor.putString(Constant.CAPTURE_IMAGE, Constant.NO);
                }
                editor.apply();
            }
        });
    }

    private void showSnackBar(final String text) {
        activity.runOnUiThread(() ->
                Util.showSnackBar(activity, text)
        );
    }

    /**
     * We've finished taking pictures from all phone's cameras
     */
    @Override
    public void onDoneCapturingAllPhotos(TreeMap<String, byte[]> picturesTaken) {
        /*if (picturesTaken != null && !picturesTaken.isEmpty()) {
            showSnackBar("Done capturing all photos!");
            return;
        }
        showSnackBar("No camera detected!");*/
    }

    /**
     * Displaying the pictures taken.
     */
    @Override
    public void onCaptureDone(String pictureUrl, byte[] pictureData) {
        if (pictureData != null) {
            activity.runOnUiThread(() -> {
                final Bitmap bitmap = BitmapFactory.decodeByteArray(pictureData, 0, pictureData.length);
//                final int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                final Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                img_front.setImageBitmap(bitmap);
//                if (pictureUrl.contains("0_pic.jpg")) {
//                    uploadBackPhoto.setImageBitmap(scaled);
//                } else if (pictureUrl.contains("1_pic.jpg")) {
//                    img_front.setImageBitmap(scaled);
//                }
            });
//            showSnackBar("Picture saved to " + pictureUrl);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_CODE: {
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkPermissions();
                }
            }
        }
    }

    /**
     * checking  permissions at Runtime.
     */
    private void checkPermissions() {
        final List<String> neededPermissions = new ArrayList<>();
        for (final String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(context,
                    permission) != PackageManager.PERMISSION_GRANTED) {
                neededPermissions.add(permission);
            }
        }
        if (!neededPermissions.isEmpty()) {
            requestPermissions(neededPermissions.toArray(new String[]{}),
                    MY_PERMISSIONS_REQUEST_ACCESS_CODE);
        }
    }
}
