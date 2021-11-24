package com.wning.demo.test;

public class NewUserModel implements IBaseUserModel {
    @Override
    public void getDiscount() {
        System.out.println("NewUserModel getDiscount");

    }

    @Override
    public void buy() {
        System.out.println("NewUserModel buy");

    }

    @Override
    public void recharge() {
        System.out.println("NewUserModel recharge");

    }
}
