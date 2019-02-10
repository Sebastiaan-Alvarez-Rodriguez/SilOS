package com.sebastiaan.silos.barcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.camerakit.CameraKitView;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

public class barcodeScanner implements CameraKitView.ImageCallback {
    private barScannerCallback callback;
    private barcodeMode mode;

    private Task<List<FirebaseVisionBarcode>> result = null;

    public barcodeScanner(barScannerCallback callback, barcodeMode mode) {
        this.callback = callback;
        this.mode = mode;
    }

    private void interpretResult(Bitmap bitmap) {
        if (result != null)
            return;
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector();
        result = detector.detectInImage(image)
                .addOnSuccessListener(firebaseVisionBarcodes -> {
                    for (FirebaseVisionBarcode barcode : firebaseVisionBarcodes) {
                        String rawValue = barcode.getRawValue();
                        Log.d("BAR_RECOGNISED", rawValue);
                    }
                    switch (mode) {
                        case MODE_FIND:
                            //TODO: somehow find in db the raw barcode-string (the correct one)
//                          callback.onDoneRecognize(product);
                            break;
                        //Insert new code
                        case MODE_INSERT:
//                          callback.onDoneRecognize(null);
                            break;
                    }
                });
        try {
            Tasks.await(result);
            result = null;
        } catch (Exception e) {
            Log.e("BARCODE_TROUBLE", e.getMessage());
        }
    }

    @Override
    public void onImage(CameraKitView cameraKitView, byte[] bytes) {
        interpretResult(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }
}
