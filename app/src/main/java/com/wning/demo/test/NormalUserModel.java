package com.wning.demo.test;

public class NormalUserModel implements IBaseUserModel {
    @Override
    public void getDiscount() {
        System.out.println("NormalUserModel NormalUserModel");

    }

    @Override
    public void buy() {
        System.out.println("NormalUserModel buy");

    }

    @Override
    public void recharge() {
        System.out.println("NormalUserModel recharge");

    }
}
