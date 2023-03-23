package com.wing.android.glide.work.net;

/**
 * 网络状态的实体bean  第一期 Jetpackt项目的源码
 */
public class NetState {

    private String responseCode;
    private boolean success = true;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
