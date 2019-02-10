package com.sebastiaan.silos.ui.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.barcode.barScannerCallback;
import com.sebastiaan.silos.barcode.barcodeMode;
import com.sebastiaan.silos.barcode.barcodeScanner;
import com.sebastiaan.silos.db.async.barcodeQueries;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class barScanActivity extends AppCompatActivity implements barScannerCallback {
    private barcodeScanner barcodeScanner;
    private CameraKitView cameraKitView;
    private Handler handler;
    private final Runnable runnable = new Runnable() {
        public void run() {
            cameraKitView.captureImage(barcodeScanner);
            handler.postDelayed(this, DELAY_CAPTURE_MILLISECONDS);
        }
    };
    private static final int DELAY_CAPTURE_MILLISECONDS = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_barcode);

        Intent intentions = getIntent();
        if (!intentions.hasExtra("barcodeMode"))
            throw new RuntimeException("Barcode activity was not given a mode");
        else {
        barcodeMode mode = (barcodeMode) intentions.getSerializableExtra("barcodeMode");
        barcodeScanner = new barcodeScanner(this, mode);

        cameraKitView = findViewById(R.id.camera_barcode_camView);
        cameraKitView.setSensorPreset(CameraKit.SENSOR_PRESET_BARCODE);
        cameraKitView.setFocus(CameraKit.FOCUS_CONTINUOUS);
        cameraKitView.requestPermissions(this);


        handler = new Handler();
        }
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
        super.onPause();
        handler.removeCallbacks(runnable);
        cameraKitView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraKitView.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDoneRecognize() {
//        barcodeQueries.getProductForBarcode task = new barcodeQueries.getProductForBarcode(manager, getApplicationContext(), product -> {
//            //TODO: Somehow send product (or rawstring) back to caller (interface?).
//        }, rawBarcode);
//        task.execute();
    }
}