package com.wning.demo.test;

public class Main {

    public static void main(String[] args) {
        UserPresenter presenter = new UserPresenter();
        //presenter.setiBaseUserModel(new NormalUserModel());
        //presenter.setiBaseUserModel(new NewUserModel());
        //presenter.setiBaseUserModel(new PlusUserModel());
        presenter.setiBaseUserModel(new DiamondUserModel());
        presenter.userBuy();
        presenter.userRecharge();
    }


}
