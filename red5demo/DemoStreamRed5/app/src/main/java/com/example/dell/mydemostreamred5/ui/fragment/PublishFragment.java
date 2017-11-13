package com.example.dell.mydemostreamred5.ui.fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.mydemostreamred5.MainActivity;
import com.example.dell.mydemostreamred5.R;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.red5pro.streaming.source.R5Camera;
import com.red5pro.streaming.source.R5Microphone;
import android.hardware.Camera;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublishFragment extends BackFragment implements SurfaceHolder.Callback {
    protected android.hardware.Camera camera = null;

    public boolean isPublishing = false;
    public R5Stream stream;
    private View v;
    private SurfaceView surfaceView;
    private Button btnPublish;
    protected int camOrientation;
    public static int MY_PERMISSION_REQUEST_CAMERA=1;
    public PublishFragment() {
        // Required empty public constructor
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static Camera getDefaultCamera(int position) {
        // Find the total number of cameras available
        int  mNumberOfCameras = Camera.getNumberOfCameras();

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < mNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == position) {
                return Camera.open(i);
            }
        }

        return null;
    }

    private void preview() {
            openFrontFacingCameraGingerbread();
            camera = openFrontFacingCameraGingerbread();
            camera.setDisplayOrientation(getCameraDisplayOrientation(getActivity(), Camera.CameraInfo.CAMERA_FACING_BACK));

            surfaceView.getHolder().addCallback(this);
            SurfaceView surface = (SurfaceView) getActivity().findViewById(R.id.sf_videoview);
            surface.getHolder().addCallback(this);

           }

    public static int getCameraDisplayOrientation(Activity activity, int cameraId) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;

        }
        return result;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static PublishFragment newInstance() {
        PublishFragment fragment = new PublishFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    protected Camera openFrontFacingCameraGingerbread() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                try {
                    cam = Camera.open(camIdx);
                    camOrientation = cameraInfo.orientation;
                    //applyDeviceRotation();
                    break;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }

        return cam;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_publish, container, false);
        btnPublish = (Button) v.findViewById(R.id.btn_publish);
        surfaceView = (SurfaceView) v.findViewById(R.id.sf_videoview);

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPuplishToggle();
            }
        });
        return v;
    }
    private void requestCameraPermission() {


        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Camera access is required for QR code scanner.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override public void onClick(View view) {
                    requestPermissions( new String[] {Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else{
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Requesting camera permission. Required for QR code scanner.",
                    Snackbar.LENGTH_SHORT).show();
            requestPermissions(new String[] {Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
        }


    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        preview();
    }

    @Override
    public void onDestroy() {
        if (camera != null) {
            camera.release();
        }
        super.onDestroy();
    }

    private void onPuplishToggle() {
        if (isPublishing)
            stopLivestream();
        else
        {
            startLivestream();

        }
        isPublishing = !isPublishing;

    }

    private void startLivestream() {
        camera.stopPreview();
        stream = new R5Stream(MainActivity.connection);
        stream.setView((SurfaceView) v.findViewById(R.id.sf_videoview));
        R5Camera r5Camera = new R5Camera(camera, 640, 480);
        r5Camera.setOrientation(getCameraDisplayOrientation(getActivity(),0));

        r5Camera.setOrientation(camOrientation+90);
       // r5Camera.setBitrate(200);

        R5Microphone r5Microphone = new R5Microphone();
        stream.attachCamera(r5Camera);
        stream.attachMic(r5Microphone);
        stream.publish("113", R5Stream.RecordType.Record);
        camera.startPreview();
        Toast.makeText(getActivity(), "Start live stream ", Toast.LENGTH_SHORT).show();

    }

    private void stopLivestream() {
        Toast.makeText(getActivity(), "Stop live stream ", Toast.LENGTH_SHORT).show();

        if (stream != null) {
            stream.stop();
            camera.startPreview();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isPublishing)
            onPuplishToggle();
    }
}
