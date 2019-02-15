package com.sebastiaan.silos.barcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.camerakit.CameraKitView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

//https://droidmentor.com/barcode-scanner-using-firebase-mlkit/
public class barcodeScanner implements CameraKitView.ImageCallback {
    private boolean busy = false;

    private barScannerCallback callback;

    private Task<List<FirebaseVisionBarcode>> result = null;

    public barcodeScanner(barScannerCallback callback) {
        this.callback = callback;
    }

    private void interpretResult(Bitmap bitmap) {
        if (busy) {
            Log.e("BARCODE", "Busy with last one still");
            return;
        }
        busy = true;
        Log.e("BARCODE_START", "Starting up. Protectron on duty.");
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector();
        result = detector.detectInImage(image)
                .addOnSuccessListener(firebaseVisionBarcodes -> {
                    for (FirebaseVisionBarcode barcode : firebaseVisionBarcodes) {
                        Log.e("BAR_RECOGNISED", "Got something!!!!!!!");
                        Log.e("BAR_RECOGNISED", "Raw: "+ barcode.getRawValue());
                        Log.e("BAR_RECOGNISED", "Display: "+barcode.getDisplayValue());
                        Log.e("BAR_RECOGNISED", "Format: "+barcode.getFormat());
                    }
                    //TODO: doe iets met die vage raw value
                    busy = false;
                    callback.onDoneRecognize(firebaseVisionBarcodes.get(0).getDisplayValue());

                })
                .addOnFailureListener(e -> {
                    Log.e("BAR_FAILED", "Failure: "+e.getMessage());
                    busy = false;
                });

    }

    @Override
    public void onImage(CameraKitView cameraKitView, byte[] bytes) {
        interpretResult(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }
}
