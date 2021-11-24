package com.wning.demo.test;

public class DiamondUserModel implements IBaseUserModel {
    @Override
    public void getDiscount() {
        System.out.println("DiamondUserModel buy");
    }

    @Override
    public void buy() {
        System.out.println("DiamondUserModel buy");
    }

    @Override
    public void recharge() {
        System.out.println("DiamondUserModel recharge");
    }
}
