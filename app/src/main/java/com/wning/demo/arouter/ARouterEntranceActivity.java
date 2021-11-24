package com.wning.demo.arouter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wning.demo.BaseActivity;
import com.wing.android.R;
import com.wing.android.databinding.ActivityArouterEntranceBinding;

@Route(path = "/router/entrance")
public class ARouterEntranceActivity extends BaseActivity<ActivityArouterEntranceBinding> implements View.OnClickListener {

    private Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                ARouter.getInstance().build("/test/activity").navigation();
                break;
        }
    }
}
