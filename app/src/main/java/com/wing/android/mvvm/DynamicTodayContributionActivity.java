package com.wing.android.mvvm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wing.android.R;
import com.wing.android.databinding.ActivityDynamicTodayContributionBinding;

public class DynamicTodayContributionActivity extends AppCompatActivity {

    private static final String TAG="DynamicTodayContributionActivity";

    private DynamicTodayContributionViewModel mViewModel;
    private ActivityDynamicTodayContributionBinding mBinding;

    private long mMinLimitMoney; //发布感谢动态的，最小齐齐豆总数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dynamic_today_contribution);
//        setTitle("近日贡献榜");
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mBinding.setIsLoading(true);
        mBinding.setIsEmpty(false);

        mViewModel= new ViewModelProvider(this).get(DynamicTodayContributionViewModel.class);
        mViewModel.getData().observe(DynamicTodayContributionActivity.this, new Observer<DynamicTodayContributionBean>() {
            @Override
            public void onChanged(final DynamicTodayContributionBean dynamicTodayContributionBeans) {
                mMinLimitMoney=dynamicTodayContributionBeans.getLimit();
                mBinding.setIsLoading(false);
                mBinding.setIsEmpty(true);
            }
        });
        mBinding.setVm(mViewModel);
        mBinding.setLifecycleOwner(this);
    }

}
