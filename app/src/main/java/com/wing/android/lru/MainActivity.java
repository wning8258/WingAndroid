//package com.wing.android.lru;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//import com.wing.android.R;
//
//public class MainActivity extends AppCompatActivity {
//
//    private ImageView imageView1, imageView2, imageView3;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        imageView1 = findViewById(R.id.image1);
//        imageView2 = findViewById(R.id.image2);
//        imageView3 = findViewById(R.id.image3);
//
//        // 使用
//        // Glide.with(this).load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg").into(imageView);
//    }
//
//    public void t1(View view) {
//        Glide.with(this).
//                load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg")
//                .into(imageView1);
//    }
//
//    public void t2(View view) {
//        Glide.with(this).
//                load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg")
//                .into(imageView2);
//    }
//
//    public void t3(View view) {
//        Glide.with(this).
//                load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg")
//                .into(imageView3);
//    }
//}
