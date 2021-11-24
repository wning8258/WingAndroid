package com.wning.demo.mvp.biz;

/**
 * Created by wning on 16/7/17.
 */

public class UserInfoBiz {

    private UserInfoBizListener mListener;

    public UserInfoBiz(UserInfoBizListener mListener) {
        this.mListener = mListener;
    }

    public void requestUserInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    if(null != mListener){
                        mListener.onSuccess();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public interface UserInfoBizListener{
        void onSuccess();
        void onfail();
    }


}
