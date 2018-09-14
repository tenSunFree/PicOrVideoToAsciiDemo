package com.example.administrator.picorvideotoasciidemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.picorvideotoasciidemo.utils.CommonUtil;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initializationView();
        initializationAsciiPic();
    }

    private void initializationView() {
        imageView = findViewById(R.id.imageView);
    }

    private void initializationAsciiPic() {
        bitmap = CommonUtil.createAsciiPic(
                CommonUtil.convertDrawable2BitmapByCanvas(getDrawable(R.drawable.jello)),
                MainActivity.this);
        imageView.setImageBitmap(bitmap);
    }
}
