package com.android.zpp.viewbnidtest;

import static androidx.camera.core.AspectRatio.RATIO_4_3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraState;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.android.zpp.viewbnidtest.databinding.CameraxLayoutBinding;
import com.android.zpp.viewbnidtest.utils.GetPhoto;
import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest
 * @ClassName: CameraXTest
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/18 13:54
 * @UpdateUser:
 * @UpdateDate: 2022/5/18 13:54
 * @UpdateRemark:
 */
public class CameraXTest extends AppCompatActivity {
    CameraxLayoutBinding cameraxLayoutBinding;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    Camera camera;
    CameraControl cameraControl;
    CameraInfo cameraInfo;
    GestureDetector gestureDetector;
    GestureDetector.OnGestureListener onGestureListener;
    ScaleGestureDetector scaleGestureDetector;
    ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener;
    ImageCapture imageCapture;
    private Executor executor;
    ArrayList<String> arrayList;
    private float mOldDist = 1f;
    Preview preview;
    CameraSelector cameraSelector;
    int Selector = CameraSelector.LENS_FACING_BACK;
    ProcessCameraProvider cameraProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        cameraxLayoutBinding = CameraxLayoutBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(cameraxLayoutBinding.getRoot());
        cameraxLayoutBinding.bg.setVisibility(View.GONE);
        executor = ContextCompat.getMainExecutor(this);
        arrayList = GetPhoto.getPhoto(this);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(CameraXTest.this, "未找到相机", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
        cameraControl();
        gestureDetector = new GestureDetector(CameraXTest
                .this, onGestureListener);
        scaleGestureDetector = new ScaleGestureDetector(CameraXTest.this, onScaleGestureListener);
        if (arrayList.size() != 0) {
            Glide.with(this)
                    .load(arrayList.get(arrayList.size() - 1))
                    .into(cameraxLayoutBinding.photo);
        }
    }

    @SuppressLint("WrongConstant")
    private void bindPreview(ProcessCameraProvider cameraProvider) {
        imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(RATIO_4_3)
                        .build();
        preview = new Preview.Builder()
                .build();
        cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(Selector)
                .build();

        preview.setSurfaceProvider(cameraxLayoutBinding.previewView.getSurfaceProvider());
        camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture, preview);
        cameraControl = camera.getCameraControl();
        cameraInfo = camera.getCameraInfo();
        Log.e("oldDelta", cameraInfo.getZoomState().getValue().getZoomRatio() + "");
        cameraInfo.getCameraState().observe(this, new Observer<CameraState>() {
            @Override
            public void onChanged(CameraState cameraState) {
                switch (cameraState.getType()) {
                    case CLOSED:
                    case OPENING:
                        cameraxLayoutBinding.bg.setVisibility(View.VISIBLE);
                        break;
                    case OPEN:
                        cameraxLayoutBinding.bg.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 相机控制
     */
    @SuppressLint("ClickableViewAccessibility")
    private void cameraControl() {
        //打开补光灯
        cameraxLayoutBinding.light.setOnClickListener(v -> {
            if (cameraInfo.getTorchState().getValue() == 0) {
                cameraControl.enableTorch(true);
                cameraxLayoutBinding.light.setImageResource(R.mipmap.light_on);
            } else {
                cameraControl.enableTorch(false);
                cameraxLayoutBinding.light.setImageResource(R.mipmap.light);
            }
        });
        //绑定手势
        cameraxLayoutBinding.previewView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            if (event.getPointerCount() == 2) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mOldDist = GetPhoto.calculateFingerSpacing(event);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float newDist = GetPhoto.calculateFingerSpacing(event);
                        if (newDist > mOldDist) {
                            handleZoom(true);
                        } else if (newDist < mOldDist) {
                            handleZoom(false);
                        }
                        break;
                    default:
                        break;
                }
            }
            return true;
        });
        onGestureListener = new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.e("手势", "onDown");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.e("手势", "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("手势", "单击对焦");
                MeteringPointFactory factory = cameraxLayoutBinding.previewView.getMeteringPointFactory();
                MeteringPoint point = factory.createPoint(e.getX(), e.getY());
                FocusMeteringAction action = new FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                        .setAutoCancelDuration(3, TimeUnit.SECONDS)
                        .build();
                cameraControl.startFocusAndMetering(action);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e("手势", "onScroll");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.e("手势", "onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.e("手势", "onFling");
                return true;
            }
        };
        cameraxLayoutBinding.takePhoto.setOnClickListener(v -> {
            String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
            ImageCapture.OutputFileOptions outputFileOptions =
                    new ImageCapture.OutputFileOptions.Builder(new File(path)).build();
            imageCapture.takePicture(outputFileOptions, executor,
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                            Log.e("拍照", "成功" + outputFileResults.getSavedUri().getPath());
                            Toast.makeText(CameraXTest.this, "拍照成功", Toast.LENGTH_SHORT).show();
                            arrayList.add(outputFileResults.getSavedUri().getPath());
                            Glide.with(CameraXTest.this)
                                    .load(arrayList.get(arrayList.size() - 1))
                                    .into(cameraxLayoutBinding.photo);
                        }

                        @Override
                        public void onError(ImageCaptureException error) {
                            Log.e("拍照", "失败" + error.getMessage());
                        }
                    }
            );
        });
        cameraxLayoutBinding.change.setOnClickListener(v -> {
            cameraxLayoutBinding.bg.setVisibility(View.VISIBLE);
            if (Selector == CameraSelector.LENS_FACING_BACK) {
                Selector = CameraSelector.LENS_FACING_FRONT;
            } else {
                Selector = CameraSelector.LENS_FACING_BACK;
            }
            cameraProvider.unbindAll();
            bindPreview(cameraProvider);
        });
    }

    /**
     * 计算缩放大小
     *
     * @param isZoomIn
     */
    private void handleZoom(boolean isZoomIn) {
        try {
            float zoom = cameraInfo.getZoomState().getValue().getLinearZoom();
            if (isZoomIn && zoom < 1.0f) {
                Log.e("缩放", "放大");
                zoom += 0.01;
            } else if (!isZoomIn && zoom > 0) {
                Log.e("缩放", "缩小");
                zoom -= 0.01;
            } else {
                Log.e("缩放", "不变");
            }
            cameraControl.setLinearZoom(zoom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
