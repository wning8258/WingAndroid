package com.wning.demo.test;

public class UserPresenter {
    private IBaseUserModel iBaseUserModel;

    public void setiBaseUserModel(IBaseUserModel iBaseUserModel) {
        this.iBaseUserModel = iBaseUserModel;
    }

    public UserPresenter() {
    }

    public UserPresenter(IBaseUserModel iBaseUserModel) {
        this.iBaseUserModel = iBaseUserModel;
    }


    public void userBuy() {
        if (iBaseUserModel != null) {
            iBaseUserModel.getDiscount();
            iBaseUserModel.buy();
        }
    }

    public void userRecharge() {
        if (iBaseUserModel != null) {
            iBaseUserModel.recharge();
        }
    }
}
