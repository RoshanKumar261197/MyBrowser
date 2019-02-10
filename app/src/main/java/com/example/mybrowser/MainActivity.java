package com.example.mybrowser;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
EditText et;
Button bt;
WebView wv;
ProgressBar pb;
TextView txt;
ImageView img;
ImageButton bk,re,fr;
GridLayout gl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gl=(GridLayout)findViewById(R.id.nav);
        txt=(TextView)findViewById(R.id.text);
        bk=(ImageButton)findViewById(R.id.back);
        re=(ImageButton)findViewById(R.id.reload);
        fr=(ImageButton)findViewById(R.id.forward);
        img=(ImageView)findViewById(R.id.image);
        et=(EditText)findViewById(R.id.search_bar);
        bt=(Button)findViewById(R.id.search_button);
        wv=(WebView)findViewById(R.id.web_view);
        pb=(ProgressBar)findViewById(R.id.progress_horizontal);
        pb.setMax(100);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                pb.setVisibility(View.GONE);
                super.onPageFinished(view, url);
                et.setText(url);
            }
        });
        wv.loadUrl("http://www.google.com");
        WebSettings webSettings=wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);

        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb.setProgress(newProgress);

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                txt.setText(title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                img.setImageBitmap(icon);
            }
        });
        wv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gl.isShown())
                {
                    gl.setVisibility(View.GONE);
                }
                else
                    {
                        gl.setVisibility(View.VISIBLE);
                    }
                return false;
            }
        });
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForwardPressed();
            }
        });
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.reload();
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=new String();
                String protocol=new String();
                protocol="https://";
                s=et.getText().toString();
                wv.setWebViewClient(new WebViewClient());
                if(s.startsWith("http"))
                {
                    wv.loadUrl(s);
                }
                else {
                    wv.loadUrl(protocol.concat(s));
                }
                et.setText(wv.getUrl());

            }
        });

    }

    public void onForwardPressed(){
        if (wv.canGoForward())
        {
            wv.goForward();
        }
        else
        {
            Toast.makeText(this, "This is the top page", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        if(wv.canGoBack())
        {

            wv.goBack();
        }
        else
        {
            finish();
        }
    }

}
