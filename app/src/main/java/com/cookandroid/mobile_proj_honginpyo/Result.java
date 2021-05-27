package com.cookandroid.mobile_proj_honginpyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

public class Result extends AppCompatActivity {
    private TextView text;
    private ImageView img;
    String filename;
    String[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        text = (TextView) findViewById(R.id.result_text);
        img = (ImageView) findViewById(R.id.result_img);


        filename = "sdcard/Accident/record.txt";
        text.setText(readRecord(filename));

    }

    String readRecord(String filename){
        String content = null;
        try{
            FileInputStream inFS = new FileInputStream(filename);
            byte txt[] = new byte[inFS.available()];
            inFS.read(txt);
            content = (new String(txt).trim());
            inFS.close();
            array = content.split("\n");
            array[0] = "상대 사고자 이름: " + array[0];
            array[1] = "상대방  전화 번호: " + array[1];
            array[2] = "상대방  차량 번호: " + array[2];
            array[3] = "사고  날짜: " + array[3];

            content = "";

            for(int i=0; i<array.length; i++){
                content += array[i] + "\n";
            }
            img.setVisibility(View.INVISIBLE);
            text.setGravity(Gravity.LEFT);

        }catch (IOException e){
            content = "저장되어 있는\n 사고 기록이 없습니다.";

        }
        return content;
    }
}