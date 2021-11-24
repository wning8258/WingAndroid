package com.wning.demo.matrix;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.guagua.modules.utils.LogUtils;
import com.wning.demo.BaseActivity;
import com.wing.android.R;
import com.wing.android.databinding.ActivityMatrixBinding;

public class MatrixActivity extends BaseActivity<ActivityMatrixBinding> implements View.OnClickListener {

    private ImageView iv;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iv= (ImageView) findViewById(R.id.iv);
        btn= (Button) findViewById(R.id.btn);

        iv.setOnClickListener(this);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv:
                Toast.makeText(getApplicationContext(),"imageview 点击事件",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn:
//                iv.getMatrix().setScale(1.5f,1.5f);
//                iv.invalidate();
//                Matrix imageMatrix = iv.getImageMatrix();   //scaletype必须是matrix才有效
//                imageMatrix.setScale(1.5f,1.5f);
//                iv.setImageMatrix(imageMatrix);
                int loc[]=new int[2];
                iv.getLocationOnScreen(loc);
                LogUtils.i(TAG,"location : "+loc[0]+" , "+loc[1]);   //还是移动之前的坐标
                Animation animation=new Animation() {

                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        t.getMatrix().setTranslate(200,200);
                        int loc[]=new int[2];
                        iv.getLocationOnScreen(loc);
                        LogUtils.i("wn","location moving : "+loc[0]+" , "+loc[1]);   //这个位置一直没有变过
                    }
                };
                animation.setDuration(2000);
                animation.setFillAfter(true);
                iv.startAnimation(animation);
                break;
        }
    }
}
