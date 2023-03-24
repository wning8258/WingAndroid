package com.wing.android.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.guagua.modules.utils.LogUtils;

public class ShortVideoDataRepository {
    private static final String TAG = "ShortVideoDataRepository";
//    private static volatile ShortVideoDataRepository mInstance;
    private ShortVideoRequest mShortVideoRequest;
    private final MutableLiveData<DynamicTodayContributionBean> mContributionLiveData = new MutableLiveData<>();  //贡献榜liveData
    private final MutableLiveData<DynamicTodayContributionBean.UserContributionBean > mUserContributionLiveData = new MutableLiveData<>(); //添加了礼物名和礼物图标的某用户消费记录liveData
    private DynamicTodayContributionBean.UserContributionBean mTempBean;  //需要进行处理的消费记录bean



    public ShortVideoDataRepository(){
        mShortVideoRequest=new ShortVideoRequest(TAG);
    }

    /**
     * 清除数据
     */
    public void clear() {

    }

    /**
     * 获取贡献榜
     * @return
     */
    public LiveData<DynamicTodayContributionBean> getDynamicTodayContribution(){
        if(mShortVideoRequest!=null){
            mShortVideoRequest.getDynamicTodayContribution();
        }
        return mContributionLiveData;

    }

    /**
     * 回调
     * @param bean
     */
    public void onGetThankContribution(DynamicTodayContributionBean bean) {
        LogUtils.i(TAG,"onGetThankContribution :"+bean);
        if(bean.getState()==0) {
            mContributionLiveData.setValue(bean);
        }else{
            mContributionLiveData.setValue(new DynamicTodayContributionBean());
        }
    }
}
