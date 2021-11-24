package com.guagua.anim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.viewbinding.ViewBinding;

import com.guagua.qiqi.gifteffect.BaseSurfaceView;
import com.wning.demo.BaseActivity;
import com.wing.android.R;
import com.wing.android.databinding.ActivityGiftEffectBinding;
import com.wning.demo.gifteffect.GiftAnimManager;

public class GiftEffectActivity extends BaseActivity<ActivityGiftEffectBinding> implements View.OnClickListener {

    private BaseSurfaceView baseSurfaceView;
    private FrameLayout baseSurfaceViewContainer;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baseSurfaceViewContainer = (FrameLayout) findViewById(R.id.gift_surface_container);
        baseSurfaceView = (BaseSurfaceView) findViewById(R.id.gift_surface);
        GiftAnimManager.getInstance(this).setToastGiftContainer(baseSurfaceView);
        btn1= (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2= (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3= (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4= (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5= (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6= (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7= (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);

    }



    @Override
    protected void onResume() {
        super.onResume();
        addGiftSurfaceView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeGiftSurfaceView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GiftAnimManager.getInstance(this).onDestroy();
        GiftAnimManager.release();
    }


    private void addGiftSurfaceView() {
        if(baseSurfaceViewContainer.getChildCount() > 0) {
            return;
        }
        baseSurfaceViewContainer.addView(baseSurfaceView);
        GiftAnimManager.getInstance(this).setToastGiftContainer(baseSurfaceView);

    }

    private void removeGiftSurfaceView() {
        GiftAnimManager.getInstance(this).setToastGiftContainer(null);
        baseSurfaceViewContainer.removeAllViews();
    }
//gift50.levelLimit = createArrayList(0, 33, 99, 288, 521, 1314, 6666, 9999);
  //  coinListMap.put(50, gift50);
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                GiftAnimManager.getInstance(this).playShapeAnim("我", "你", 33,
                "个", "http://down.qxiu.com/pic/7419_9.png", 50);
                break;
            case R.id.btn2:
                GiftAnimManager.getInstance(this).playShapeAnim("我", "你", 99,
                "个", "http://down.qxiu.com/pic/7419_9.png", 50);
                break;
            case R.id.btn3:
                GiftAnimManager.getInstance(this).playShapeAnim("我", "你", 288,
                "个", "http://down.qxiu.com/pic/7419_9.png", 50);
                break;
            case R.id.btn4:
                GiftAnimManager.getInstance(this).playShapeAnim("我", "你", 521,
                        "个", "http://down.qxiu.com/pic/7419_9.png", 50);
                break;
            case R.id.btn5:
                GiftAnimManager.getInstance(this).playShapeAnim("我", "你", 1314,
                        "个", "http://down.qxiu.com/pic/7419_9.png", 50);
                break;
            case R.id.btn6:
                GiftAnimManager.getInstance(this).playShapeAnim("我", "你", 6666,
                        "个", "http://down.qxiu.com/pic/7419_9.png", 50);
                break;
            case R.id.btn7:
                GiftAnimManager.getInstance(this).playShapeAnim("我", "你", 9999,
                        "个", "http://down.qxiu.com/pic/7419_9.png", 50);
                break;
        }
    }
}
