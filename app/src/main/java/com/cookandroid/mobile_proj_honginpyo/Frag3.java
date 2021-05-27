package com.cookandroid.mobile_proj_honginpyo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Frag3 extends Fragment {
    private View view;
    File myFolder;
    private EditText name, tel,car;
    private Button btn;


    String foldername = "sdcard/Accident";
    String filename = ".txt";

    public static Frag3 newinstance(){
        Frag3 frag3 = new Frag3();
        return frag3;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 위험보안 설정요구 질문
        if (Build.VERSION.SDK_INT >= 26) {
            int pCheck = ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (pCheck == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE}, getActivity().MODE_PRIVATE);
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

        view = inflater.inflate(R.layout.frag_3, container, false);

        name = (EditText)view.findViewById(R.id.frag_3_name_input);
        tel = (EditText)view.findViewById(R.id.frag_3_tel_input);
        car = (EditText)view.findViewById(R.id.frag_3_car_input);
        btn = (Button)view.findViewById(R.id.frag_3_btn_save);


       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

               String name_string = name.getText().toString();
               String tel_string = tel.getText().toString();
               String car_string = car.getText().toString();

               try{
                   FileOutputStream outFS = new FileOutputStream(foldername+"/record"+filename);
                   String contents = name_string +"\n" + tel_string + "\n" + car_string + "\n"+ now+ "\n";
                   outFS.write(contents.getBytes());
                   outFS.close();
                   showToast("상대방의 정보가 저장되었습니다.");
                   name.setText("");
                   tel.setText("");
                   car.setText("");
               }catch (IOException e){
                   showToast("파일을 저장할 수 없습니다.");
                   e.printStackTrace();
               }


           }
       });


       return view;
    }
    void showToast(String msg){
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            showToast("sd카드 접근 거부");
        } else {
            sdcardProcess();
        }
    }

    void sdcardProcess() {
        foldername = "sdcard";
        foldername = foldername+"/Accident";
        myFolder = new File(foldername);
        if (!myFolder.isDirectory()){ //폴더 없을때
            myFolder.mkdir(); //폴더생성
            showToast(foldername);
        }
    }
}
