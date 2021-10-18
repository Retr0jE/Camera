package com.example.cumera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telecom.RemoteConnection;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private int CAMERA_CAPTURE;
    private int CAMERA_REC;

    Button btn;
    ImageView img;
    Button btnrec;
    VideoView vidvi;
    int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnrec = findViewById(R.id.btnRecord);
        btn = findViewById(R.id.btnTakePhoto);
        img = findViewById(R.id.img);
        vidvi = findViewById(R.id.vid);
        btn.setOnClickListener(view -> {
            try {
//Используем стандартное системное намерение на использование камеры:
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//Задаем возможность работать с полученными с камеры данными:
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            } catch (ActivityNotFoundException cant) {
//Показываем сообщение об ошибке:
                String errorMessage = "Камера не поддерживается вашим устройством!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
            k = 0;
        });
        btnrec.setOnClickListener(view -> {
            try {
//Используем стандартное системное намерение на использование камеры:
                Intent captureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//Задаем возможность работать с полученными с камеры данными:
                startActivityForResult(captureIntent, CAMERA_REC);
            } catch (ActivityNotFoundException cant) {
//Показываем сообщение об ошибке:
                String errorMessage = "Камера не поддерживается вашим устройством!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
            k = 1;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (k == 0) {
            Bundle extras = data.getExtras();
            Bitmap thumbnailBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(thumbnailBitmap);
        } else {
            Uri vidUri = (Uri) data.getData();
            vidvi.setVideoURI(vidUri);
            vidvi.setZOrderOnTop(true);
            vidvi.start();
        }

    }
}