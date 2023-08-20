package com.example.torch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton;
    boolean hasflashon = false;
    boolean hascamflash = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.onid);
        hascamflash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hascamflash){
                    if(hasflashon){
                        hasflashon=false;
                        imageButton.setImageResource(R.drawable.onoff);
                        try {
                            flashoff();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        hasflashon = true;
                        imageButton.setImageResource(R.drawable.on_button);
                        try {
                            flashon();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "No flashlight available for your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void flashon() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String camid = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(camid, true);
        Toast.makeText(MainActivity.this, "Flashlight is ON", Toast.LENGTH_SHORT).show();
    }

    private void flashoff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String camid = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(camid, false);
        Toast.makeText(MainActivity.this, "Flashlight is OFF", Toast.LENGTH_SHORT).show();
    }
}