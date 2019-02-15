package com.sebastiaan.silos.db.async;

import android.content.Context;

import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.entities.product;

public class barcodeQueries {
    public static class insertBarcodeTask extends DbAsyncTask<Void> {
        private final barcode barcode;
        public insertBarcodeTask(AsyncManager manager, Context context, DbAsyncInterface<Void> dai, barcode barcode) {
            super(manager, context);
            this.Dbcallback = dai;
            this.barcode = barcode;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.barcodeDao().insert(barcode);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Dbcallback.onComplete(result);
        }
    }

    public static class getProductForBarcode extends DbAsyncTask<product> {
        private final String rawBarcode;

        public getProductForBarcode(AsyncManager manager, Context context, DbAsyncInterface<product> dai, String rawBarcode) {
            super(manager, context);
            this.Dbcallback = dai;
            this.rawBarcode = rawBarcode;
        }

        @Override
        protected product doInBackground(Void... voids) {
            return database.barcodeDao().findProductByBarcode(rawBarcode);
        }

        @Override
        protected void onPostExecute(product product) {
            super.onPostExecute(product);
            Dbcallback.onComplete(product);
        }
    }

}
