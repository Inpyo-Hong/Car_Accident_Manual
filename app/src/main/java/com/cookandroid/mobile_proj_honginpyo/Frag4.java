package com.cookandroid.mobile_proj_honginpyo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class Frag4 extends Fragment{
    private View view;

    private static int MY_PERMISSION_REQUEST_LOCATION = 0;
    private String tel = "tel:1588-1234";

    private Button btn_tel1,btn_tel2,btn_tel3,btn_tel4,btn_tel5,btn_tel6,btn_tel7,btn_tel8;

    private WebView webView;
    private String url = "https://maps.google.com";

    public static Frag4 newinstance() {
        Frag4 frag4 = new Frag4();
        return frag4;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_4, container, false);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.KEYCODE_BACK) && (webView.canGoBack())) {
                    return true;
                }
                else
                    return false;
            }
        });

        btn_tel1 = (Button) view.findViewById(R.id.tel_1);
        btn_tel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1588-5114"));
                startActivity(intent);
            }
        });

        btn_tel2 = (Button) view.findViewById(R.id.tel_2);
        btn_tel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1588-0100"));
                startActivity(intent);
            }
        });

        btn_tel3 = (Button) view.findViewById(R.id.tel_3);
        btn_tel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1588-3344"));
                startActivity(intent);
            }
        });

        btn_tel4 = (Button) view.findViewById(R.id.tel_4);
        btn_tel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1588-4982"));
                startActivity(intent);
            }
        });

        btn_tel5 = (Button) view.findViewById(R.id.tel_5);
        btn_tel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1566-8000"));
                startActivity(intent);
            }
        });

        btn_tel6 = (Button) view.findViewById(R.id.tel_6);
        btn_tel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1544-0114"));
                startActivity(intent);
            }
        });

        btn_tel7 = (Button) view.findViewById(R.id.tel_7);
        btn_tel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:112"));
                startActivity(intent);
            }
        });

        btn_tel8 = (Button) view.findViewById(R.id.tel_8);
        btn_tel8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:119"));
                startActivity(intent);
            }
        });


        webView = (WebView) view.findViewById(R.id.webview);




        permissionCheck();

        return view;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_PERMISSION_REQUEST_LOCATION){
            initWebView();
        }

    }

    private void permissionCheck(){
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            initWebView();
        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
        }
    }

    private void initWebView(){
        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용을 허용
        webView.setWebChromeClient(new WebChromeClient(){ @Override public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
            callback.invoke(origin, true, false);
        }
        });

        webView.setWebChromeClient(new WebChromeClientClass());
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClientClass());
        webView.reload();


        }


    private class WebViewClientClass extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
           handler.proceed();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {//현재 페이지 url 읽어오는 기능
            view.loadUrl(url);
            return true;
        }
    }

    private class WebChromeClientClass extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            final String myOrigin = origin;
            final GeolocationPermissions.Callback myCallback = callback;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("위치권한 설정");
            builder.setMessage("현재위치 권한을 허용하시겠습니까?");
            builder.setPositiveButton("허용", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    myCallback.invoke(myOrigin,true, false);
                }
            });
            builder.setNegativeButton("거절", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    myCallback.invoke(myOrigin,false,false);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}

