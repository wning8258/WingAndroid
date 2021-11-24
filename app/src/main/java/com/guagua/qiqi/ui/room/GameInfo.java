package com.guagua.qiqi.ui.room;

import android.text.TextUtils;

import com.wning.demo.gifteffect.BaseBean;

import org.json.JSONObject;

/**
 * Created by wning on 2016/10/18.
 */

public class GameInfo extends BaseBean {

    //豪车漂移初始化json参数的key
    public static final String MCAR_INIT_ROLL = "roll";
    public static final String MCAR_INIT_STATUS = "status";
    public static final String MCAR_INIT_INTERVAL = "interval";

    public String title;
    public String icon;
    public String url;
    public String actId;
    public String type;
    public String from;

    private boolean isRoll;
    private boolean isShow;
    private long rollInterval;

    public GameInfo() {

    }

    public GameInfo setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public GameInfo(JSONObject json) {
        title = getString(json, "title");
        icon = getString(json, "icon");
        url = getString(json,"url");
        actId = getString(json,"actId");
        type = getString(json,"type");
        from = getString(json,"from");
//        try {
//            JSONObject initJson = json.getJSONObject("init");
//            if(initJson!=null){
//                isRoll = ProtocolParser.getJsonInt(initJson, MCAR_INIT_ROLL) == 1;
//                isShow = ProtocolParser.getJsonInt(initJson, MCAR_INIT_STATUS) == 1;
//                rollInterval = ProtocolParser.getJsonInt(initJson, MCAR_INIT_INTERVAL) * 1000;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public boolean isShow() {
//        if (mSwitch != 1) {
//            return false;
//        }
        return isShow;
    }

    public boolean isScroll() {
        if (!isShow()) {
            return false;
        }
        return isRoll;
    }

    public long getRollInterval() {
        return rollInterval;
    }

    public boolean isFromGame(){
        return !TextUtils.isEmpty(from)&&from.equals("1");
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "actId='" + actId + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", isRoll=" + isRoll +
                ", isShow=" + isShow +
                ", rollInterval=" + rollInterval +
                '}';
    }
}

