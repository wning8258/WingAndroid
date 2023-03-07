package com.wning.demo.arouter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.wing.android.databinding.ActivityArouterBinding;
import com.wning.demo.BaseActivity;

@Route(path = "/test/activity2")
public class ARouterTestActivity extends BaseActivity<ActivityArouterBinding>{

    @Autowired
    String key;



    //Arouter Master

}
