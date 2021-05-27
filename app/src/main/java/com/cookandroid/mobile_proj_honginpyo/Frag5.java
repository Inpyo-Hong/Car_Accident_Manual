package com.cookandroid.mobile_proj_honginpyo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.media.MediaScannerConnection;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_PICTURES;

public class Frag5 extends Fragment {
    private View view;

    public static int i=0;
    private ImageView mybtn;

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;

    private MediaScanner nMediaScanner;

    public static Frag5 newinstance(){
        Frag5 frag5 = new Frag5();
        return frag5;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.frag_5, container, false);


       nMediaScanner = MediaScanner.getInstance(getActivity().getApplicationContext());


        TedPermission.with(getActivity().getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

       view.findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                   File photoFile = null;
                   try{
                       photoFile = createImageFile();
                   }catch (IOException e){

                   }

                   if(photoFile != null){
                       photoUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getPackageName(), photoFile);
                       intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                       startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                   }
               }
           }
       });


        return view;
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd__HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(DIRECTORY_PICTURES);
        File image = new File(storageDir,imageFileName+".jpg");
        String filepath = image.getPath();
        Log.i("사진파일경로: ",filepath );
        if(!image.exists()){
            image.createNewFile();
        }
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try{
                exif = new ExifInterface(imageFilePath);
            }catch (IOException e){
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if(exif != null){

                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegress(exifOrientation);
            }else{
                exifDegree = 0;
            }

            String result = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmss", Locale.getDefault() );
            Date             curDate   = new Date(System.currentTimeMillis());
            String           filename  = formatter.format(curDate);

            String           strFolderName = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + File.separator + "ACCIDENT_IMG" + File.separator;
            File file = new File(strFolderName);
            if( !file.exists() )
                file.mkdirs();

            File f = new File(strFolderName + "/" + filename + ".png");
            result = f.getPath();

            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                result = "Save Error fOut";
            }

            // 비트맵 사진 폴더 경로에 저장
            rotate(bitmap,exifDegree).compress(Bitmap.CompressFormat.PNG, 70, fOut);

            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
                // 방금 저장된 사진을 갤러리 폴더 반영 및 최신화
                nMediaScanner.mediaScanning(strFolderName + "/" + filename + ".png");
            } catch (IOException e) {
                e.printStackTrace();
                result = "File close Error";
            }

            switch (i){
                case 0:
                    mybtn = view.findViewById(R.id.btn_frag5_1_c);
                    mybtn.setVisibility(View.VISIBLE);
                    i++;
                    break;
                case 1:
                    mybtn = view.findViewById(R.id.btn_frag5_2_c);
                    mybtn.setVisibility(View.VISIBLE);
                    i++;
                    break;
                case 2:
                    mybtn = view.findViewById(R.id.btn_frag5_3_c);
                    mybtn.setVisibility(View.VISIBLE);
                    i++;
                    break;
                case 3:
                    mybtn = view.findViewById(R.id.btn_frag5_4_c);
                    mybtn.setVisibility(View.VISIBLE);
                    i++;
                    break;
                default:
                    break;
            }

            ((ImageView) view.findViewById(R.id.frag_5_result)).setImageBitmap(rotate(bitmap,exifDegree));

        }
    }

    private int exifOrientationToDegress(int exifOrientation){
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0,0,bitmap.getWidth(),bitmap.getHeight(), matrix,true);
    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getActivity().getApplicationContext(),"권한이 허용됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getActivity().getApplicationContext(),"권한이 거절됨", Toast.LENGTH_SHORT).show();
        }
    };
}
