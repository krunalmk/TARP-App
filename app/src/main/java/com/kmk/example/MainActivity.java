package com.kmk.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.kmk.awesomescanner.ui.ScannerView;
import com.kmk.awesomescanner.ui.GpCodeScanner;
import com.kmk.awesomescanner.util.DecodeCallback;

public class MainActivity extends AppCompatActivity {

    private TextView txtScanText;
    private Button connectBtn;

    private GpCodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtScanText = findViewById(R.id.text);
        connectBtn = findViewById(R.id.connect_btn);
//        connectBtn.setVisibility(View.INVISIBLE);

        RequestCameraPermission requestCameraPermission=new RequestCameraPermission(this);

        if(requestCameraPermission.verifyCameraPermission()) {

            ScannerView scannerView = findViewById(R.id.scanner_view);
            mCodeScanner = new GpCodeScanner(this, scannerView);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
                            txtScanText.setText("" + result.getText());
                            connectBtn.setVisibility(View.VISIBLE);
                            connectBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.249.22:8000/query?code="+result.getText()));
                                    startActivity(browserIntent);
                                }
                            });
                        }
                    });
                }
            });

            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"Camera Permission required", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCodeScanner!=null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if(mCodeScanner!=null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }
}
/*


class Main
{
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://192.168.27.58:8000/query?code=qwerty");
        URLConnection connection = url.openConnection();

        try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())))
        {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
 */