package com.wing.android.bindpool;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}