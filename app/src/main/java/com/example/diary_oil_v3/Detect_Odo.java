package com.example.diary_oil_v3;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import env.ImageUtils;
import env.Logger;
import env.Utils;
import tflite.Classifier;
import tflite.YoloV4Classifier;
import tracking.MultiBoxTracker;

public class Detect_Odo extends AppCompatActivity {

    public static void Detect_Odo()
    {

    }

    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.3f;
    private static final Logger LOGGER = new Logger();

    public static final int TF_OD_API_INPUT_SIZE = 416;

    private static final boolean TF_OD_API_IS_QUANTIZED = true;

    private static final String TF_OD_API_MODEL_FILE = "detect.tflite";

    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/labelmap.txt";

    // Minimum detection confidence to track a detection.
    private static final boolean MAINTAIN_ASPECT = false;
    private static final Integer sensorOrientation = 90;

    private  static Classifier detector;

    private static Matrix frameToCropTransform;
    private static Matrix cropToFrameTransform;
    private static MultiBoxTracker tracker;
    //private OverlayView trackingOverlay;

    protected static int previewWidth = 0;
    protected static int previewHeight = 0;

    private static Bitmap sourceBitmap;
    private static Bitmap cropBitmap;

    public void initBox() {
        previewHeight = TF_OD_API_INPUT_SIZE;
        previewWidth = TF_OD_API_INPUT_SIZE;
        frameToCropTransform =
                ImageUtils.getTransformationMatrix(
                        previewWidth, previewHeight,
                        TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE,
                        sensorOrientation, MAINTAIN_ASPECT);
        try {
            detector =
                    YoloV4Classifier.create(
                            getAssets(),
                            TF_OD_API_MODEL_FILE,
                            TF_OD_API_LABELS_FILE,
                            TF_OD_API_IS_QUANTIZED);
        } catch (final IOException e) {
            e.printStackTrace();
            LOGGER.e(e, "Exception initializing classifier!");
            Toast toast =
                    Toast.makeText(
                            getApplicationContext(), "Classifier could not be initialized", Toast.LENGTH_SHORT);
            toast.show();

        }
    }

    public void GetBitmapFromDir(Uri dat)
    {

        Bitmap image = null;
        try {
            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
            sourceBitmap = image;
            if (Utils.checkRotate(this.sourceBitmap))
                sourceBitmap = Utils.rotateBitmap(this.sourceBitmap, 90);
            cropBitmap = Utils.processBitmap(sourceBitmap, TF_OD_API_INPUT_SIZE);

             //return bitmap image

        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
