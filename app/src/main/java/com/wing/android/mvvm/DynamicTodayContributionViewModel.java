package com.wing.android.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DynamicTodayContributionViewModel extends AndroidViewModel {
    private static final String TAG = "DynamicTodayContributionViewModel";
    private ShortVideoDataRepository mDataRepository;
   private LiveData<DynamicTodayContributionBean>  data;


    public BindingUtils.OnRefreshCommand  onRefreshCommand=new BindingUtils.OnRefreshCommand() {
        @Override
        public void execute() {
//            mDataRepository.onRefresh().observe(mLifecycleOwner, new Observer<Boolean>() {
//                @Override
//                public void onChanged(Boolean aBoolean) {
//                    isRefreshEnd.setValue(aBoolean);
//                }
//            });
        }
    };

    public BindingUtils.OnLoadMoreCommand  onLoadMoreCommand=new BindingUtils.OnLoadMoreCommand() {
        @Override
        public void execute() {
//            mDataRepository.onLoadMore().observe(mLifecycleOwner, new Observer<LoadMoreInfo>() {
//                @Override
//                public void onChanged(LoadMoreInfo loadMoreInfo) {
//                    isLoadMoreEnd.setValue(loadMoreInfo);
//                }
//            });
        }
    };


    public DynamicTodayContributionViewModel(@NonNull Application application) {
        super(application);
        mDataRepository=new ShortVideoDataRepository();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(mDataRepository!=null){
            mDataRepository.clear();
        }
    }


    /**
     * 获取数据
     * @return
     */
    public LiveData<DynamicTodayContributionBean> getData(){
        data=mDataRepository.getDynamicTodayContribution();
        return data;
    }



}
