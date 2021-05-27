package com.cookandroid.mobile_proj_honginpyo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public TextView tel1;
    File myFolder;
    String foldername;
    String filename = ".txt";

    private Button btn_manual, btn_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위험보안 설정요구 질문
        if (Build.VERSION.SDK_INT >= 26) {
            int pCheck = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (pCheck == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
            } else {
                foldername = "sdcard";
                foldername = foldername+"/Accident";
                myFolder = new File(foldername);
                if (!myFolder.isDirectory()){
                    myFolder.mkdir();
                }
            }
        }else{
            foldername = "sdcard";
            foldername = foldername+"/Accident";
            myFolder = new File(foldername);
            if (!myFolder.isDirectory()){
                myFolder.mkdir();
            }
        }

        btn_manual = findViewById(R.id.btn_manual);
        btn_record = findViewById(R.id.btn_record);

        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Manual.class);
                startActivity(intent);  //액티비티 이동
            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Result.class);
                startActivity(intent);
            }
        });
    }
}