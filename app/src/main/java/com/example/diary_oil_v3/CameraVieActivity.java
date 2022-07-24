package com.example.diary_oil_v3;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import env.ImageUtils;
import env.Logger;
import env.Utils;
import tflite.Classifier;
import tflite.YoloV4Classifier;
import tracking.MultiBoxTracker;

import com.google.common.util.concurrent.ListenableFuture;

// import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


public class CameraVieActivity extends AppCompatActivity {

    private Uri uri;
    private File saved_pic;

    private ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    private Button snap ;

    private PreviewView previewView;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBox();
        setContentView(R.layout.camera_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
        || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
        || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,1000);
        }}
        snap = (Button) findViewById(R.id.snapview);
        previewView = (PreviewView) findViewById(R.id.previewview);

        cameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderListenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, getExecutor());
        snap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            capturePhoto();


            //detect_moment();
            }
        });
    }


    private  void capturePhoto()
    {
        GetBitmapFromDir();
    }

    private void cacpturePhoto() {
        long timestamp = System.currentTimeMillis();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "temp_pic");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "Diary_oil";

        File outputDir = new File(path);


        Toast.makeText(CameraVieActivity.this, outputDir.exists()+"", Toast.LENGTH_SHORT).show();
        //create dir if not there
        if (!outputDir.exists()) {
            outputDir.mkdir();

        }

        File file = new File(path+File.separator+"/temp_pic.jpg");
        Uri outputdir = Uri.fromFile(outputDir);
        Toast.makeText(CameraVieActivity.this, outputdir.getPath()+"", Toast.LENGTH_SHORT).show();



        boolean bool = file.delete();
        //Toast.makeText(CameraVieActivity.this, file.getAbsolutePath() + file.exists(), Toast.LENGTH_SHORT).show();


        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(CameraVieActivity.this, "Photo has been saved successfully " , Toast.LENGTH_SHORT).show();

                        //uri = outputFileResults.getSavedUri();
                        saved_pic = file;
                        GetBitmapFromDir();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraVieActivity.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Preview preview = new Preview.Builder().build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this,cameraSelector,preview, imageCapture);

    }

    private Executor getExecutor()
    {
        return ContextCompat.getMainExecutor(this);
    }


    // Detect at here
    private static final Logger LOGGER = new Logger();

    public static final int TF_OD_API_INPUT_SIZE = 416;

    private static final boolean TF_OD_API_IS_QUANTIZED = true;

    private static final String TF_OD_API_MODEL_FILE = "detect.tflite";

    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/labelmap.txt";

    // Minimum detection confidence to track a detection.
    private static final boolean MAINTAIN_ASPECT = false;
    private final Integer sensorOrientation = 90;

    private Classifier detector;

    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;
    private MultiBoxTracker tracker;
    //private OverlayView trackingOverlay;

    protected int previewWidth = 0;
    protected int previewHeight = 0;

    private Bitmap sourceBitmap;
    private Bitmap cropBitmap;


    private void initBox() {
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

    private boolean detectable;
    private String odometer;

    private void GetBitmapFromDir()
    {

        Bitmap image = null;
        detectable = false;

        //image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
        //sourceBitmap = image;
        sourceBitmap = previewView.getBitmap();
        if (Utils.checkRotate(this.sourceBitmap))
            sourceBitmap = Utils.rotateBitmap(this.sourceBitmap, 90);
        cropBitmap = Bitmap.createScaledBitmap(sourceBitmap, TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE, true);
        cropBitmap = Utils.processBitmap(sourceBitmap,TF_OD_API_INPUT_SIZE);
            /*
            Toast.makeText(CameraVieActivity.this, "Cumming" , Toast.LENGTH_SHORT).show();
            Log.d("debug", "V cropped_width: " + cropBitmap.getWidth() + " cropped_height: " + cropBitmap.getHeight());
            */

/*
        ImageView imageView;
        imageView = (ImageView) findViewById(R.id.Minh);
        imageView.setImageBitmap(cropBitmap);
*/

        detect_moment();


    }

    private void detect_moment()
    { //detect btn for image
        Handler handler = new Handler();

        new Thread(() -> { //START HERE
            final List<Classifier.Recognition> results = detector.recognizeImage(cropBitmap); //get detection results
            String digits = readDigits(results);
            //Log.d("debug","Results = "+results.toString());
            //Log.d("debug","Digits = "+digits.toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String odo = digits_check(digits);

                    //display number on screen
//                      handleResult(cropBitmap, results);\
                    if (odo == "")
                    {
                        detectable = false;
                        Toast.makeText(CameraVieActivity.this, "Error at detect, pls take another picture /n"+"Detect "+digits, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        detectable = true;
                        odometer = odo;
                        popup_alert();
                    }
                }
            });




        }).start();

    }


    private String readDigits(List<Classifier.Recognition> results) {
        String res = "";
        boolean isOdometerDetected = false;
        RectF odometerCoors = null;

        for (Classifier.Recognition result : results) {
            if (result.getTitle().equals("odometer")) {
                isOdometerDetected = true;
                odometerCoors = result.getLocation();
                //Toast.makeText(CameraVieActivity.this, "Pissing" , Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if (isOdometerDetected) {
            HashMap<Float, String> classes = new HashMap<>();
            Bitmap croppedOdometer = Utils.cropImage(sourceBitmap, cropBitmap, odometerCoors); //crop out the odometer region
            Log.d("debug", "cropped_width: " + croppedOdometer.getWidth() + " cropped_height: " + croppedOdometer.getHeight());

            //croppedOdometer = Bitmap.createScaledBitmap(croppedOdometer, TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE, true); //resize img
            croppedOdometer = Utils.processBitmap(croppedOdometer,TF_OD_API_INPUT_SIZE);
            List<Classifier.Recognition> resultsDigit = detector.recognizeImage(croppedOdometer);

            croped=croppedOdometer;
            for (Classifier.Recognition result : resultsDigit) {
                Float xPos = result.getLocation().left;
//            Float confidence = result.getConfidence();
                String className = result.getTitle();
                classes.put(xPos, className);
            }
            List<Float> xPositions = new ArrayList<>(classes.keySet());
            Collections.sort(xPositions);
            for (int i = 0; i < xPositions.size(); i++) {
                Float xPos = xPositions.get(i);
                String className = classes.get(xPos);
                if (!className.equals("odometer")) res += className;
            }
            Log.d("debug", res);
            //handleResult(croppedOdometer, resultsDigit); //display detected image on screen
        }

        return res;
    }

    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.3f;
    private Bitmap croped;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LAST_RECORD_ODO = "lastrecordodo";
    public static final String LAST_RECORD_DATE = "lastrecorddate";
    private String digits_check(String d)
    {
        int a = d.length();
        if (a == 6 || a==7)
        {
            return d.substring(0,5);
        }
        if (a== 5)
        {
            return d;
        }
        else
        {
            return "";
        }
    }

    private void popup_alert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraVieActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Odometer detected ");
        builder.setMessage("Confirm : "+odometer);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(LAST_RECORD_ODO, odometer);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                        String currentDateandTime = sdf.format(new Date());
                        editor.putString(LAST_RECORD_DATE, currentDateandTime);




                        editor.apply();

                        Intent intent = new Intent(CameraVieActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}