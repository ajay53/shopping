package com.example.shopping.service;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.example.shopping.listener.PictureCapturingListener;

public abstract class ACameraService {

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private final Activity activity;
    final Context context;
    final CameraManager manager;

    /***
     * constructor.
     *
     * @param activity the activity used to get display manager and the application context
     */
    ACameraService(final Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    /***
     * @return orientation
     */
    int getOrientation() {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        final int rotation = this.activity.getWindowManager().getDefaultDisplay().getRotation();
        return ORIENTATIONS.get(rotation);
    }


    /**
     * starts pictures capturing process.
     *
     * @param listener picture capturing listener
     */
    public abstract void startCapturing(final PictureCapturingListener listener);
}
