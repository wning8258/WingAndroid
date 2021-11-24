package com.wning.demo.arouter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.wning.demo.BaseActivity;
import com.wing.android.R;
import com.wing.android.databinding.ActivityArouterBinding;
import com.wing.android.databinding.ActivityLooperBinding;

@Route(path = "/test/activity")
public class ARouterActivity extends BaseActivity<ActivityArouterBinding>{

    @Autowired
    String key;



    //Arouter Master

}
