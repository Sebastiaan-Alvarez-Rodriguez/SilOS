package com.sebastiaan.silos.ui.barcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.barcode.barScannerCallback;
import com.sebastiaan.silos.barcode.barcodeScanner;
import com.sebastiaan.silos.ui.resultCodes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class barScanActivity extends AppCompatActivity implements barScannerCallback {
    private barcodeScanner barcodeScanner;
    private CameraKitView cameraKitView;
    private Handler handler;
    private final Runnable runnable = new Runnable() {
        public void run() {
            Log.e("CAMERA", "Camera fired!");
            cameraKitView.captureImage(barcodeScanner);
            handler.postDelayed(this, DELAY_CAPTURE_MILLISECONDS);
        }
    };
    private static final int DELAY_CAPTURE_MILLISECONDS = 5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.camera_barcode);

        barcodeScanner = new barcodeScanner(this);

        cameraKitView = findViewById(R.id.camera_barcode_camView);
        cameraKitView.setSensorPreset(CameraKit.SENSOR_PRESET_BARCODE);
        cameraKitView.setFocus(CameraKit.FOCUS_CONTINUOUS);
        cameraKitView.requestPermissions(this);


        handler = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
        handler.postDelayed(runnable, DELAY_CAPTURE_MILLISECONDS);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDoneRecognize(String displayValue) {
        shortVibrate();
        activityFinish(displayValue);
    }

    private void activityFinish(String displayValue) {
        Bundle bundle = new Bundle();
        bundle.putString("result", displayValue);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(resultCodes.OK, intent);
        finish();
    }

    private void shortVibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator != null && vibrator.hasVibrator())
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}