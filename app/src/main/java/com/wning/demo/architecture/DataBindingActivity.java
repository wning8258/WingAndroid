package com.wning.demo.architecture;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.guagua.modules.utils.LogUtils;
import com.wing.android.R;
import com.wing.android.databinding.ActivityDatabindingBindingImpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


public class DataBindingActivity extends AppCompatActivity {


    private static final String TAG="DataBindingActivity";

    private UserBean user;

    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDatabindingBindingImpl binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);

        user=new UserBean("张三",26);
        binding.setUser(user);


        LogUtils.i(TAG,TAG+" onCreate");

        listener=new LocationListener();

        getLifecycle().addObserver(listener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG,TAG+" onDestroy");
      //  getLifecycle().removeObserver(listener);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.nameView:
                Toast.makeText(getApplicationContext(),"改变姓名为李四",Toast.LENGTH_SHORT).show();
                user.setName("李四");
                break;
            case R.id.ageView:
                Toast.makeText(getApplicationContext(),"改变年龄为18",Toast.LENGTH_SHORT).show();
                user.setAge(18);
                break;
        }

    }




}
