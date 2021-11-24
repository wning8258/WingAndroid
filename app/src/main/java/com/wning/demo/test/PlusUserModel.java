package com.wning.demo.test;

public class PlusUserModel implements IBaseUserModel {
    @Override
    public void getDiscount() {
        System.out.println("PlusUserModel getDiscount");

    }

    @Override
    public void buy() {
        System.out.println("PlusUserModel buy");

    }

    @Override
    public void recharge() {
        System.out.println("PlusUserModel recharge");

    }
}
