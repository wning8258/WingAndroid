package com.wing.android.mvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.wing.android.BR;

import org.json.JSONObject;

import java.util.ArrayList;

public class DynamicTodayContributionBean extends BaseObservable {
    private int state;
    private int status;
    private String message;
    private String anchorId;
    private String totalAmount;
    private long limit;  //发布感谢动态的最小豆数，0 是不允许发 ,-1 不限制，大于0是最小豆数
    private ArrayList<UserContributionBean> mUserContributionList;

    public DynamicTodayContributionBean(JSONObject rootJson) {
        super();
        if(rootJson==null){
            return;
        }
//        status=getInt(rootJson,"status");
//        state=getInt(rootJson,"state");
//        message=getString(rootJson,"message");
//        if(status==200&&state==0){
//            JSONObject contentJson=getJSONObject(rootJson,"content");
//            if(contentJson!=null) {
//                anchorId=getString(contentJson,"anchorId");
//                totalAmount=getString(contentJson,"totalAmount");
//                limit=getLong(contentJson,"limit");
//                JSONArray itemsJson = getArray(contentJson, "userItems");
//                if (itemsJson != null) {
//                    int num = itemsJson.length();
//                    mUserContributionList = new ArrayList<>();
//                    UserContributionBean itemBean;
//                    for (int i = 0; i < num; i++) {
//                        itemBean = new UserContributionBean(getJSONObject(itemsJson, i));
//                        mUserContributionList.add(itemBean);
//                    }
//                }
//            }
//        }
    }

    public DynamicTodayContributionBean() {
        mUserContributionList=new ArrayList<>();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public ArrayList<UserContributionBean> getmUserContributionList() {
        return mUserContributionList;
    }

    public void setmUserContributionList(ArrayList<UserContributionBean> mUserContributionList) {
        this.mUserContributionList = mUserContributionList;
    }

    @Override
    public String toString() {
        return "DynamicTodayContributionBean{" +
                "state=" + state +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", anchorId='" + anchorId + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", limit='" + limit + '\'' +
                ", mUserContributionList=" + mUserContributionList +
                '}';
    }

    public static class UserContributionBean extends BaseObservable {
        private String amount;
        private String headpic;
        private int level;
        private String name;
        private String userId;
        private int invisibility;//是否隐身，1为隐身，隐身时不可感谢
        private ArrayList<UserConsumeBean> mUserConsumeList;

        public UserContributionBean(JSONObject rootJson) {
            super();
//            amount=getString(rootJson,"amount");
//            headpic=getString(rootJson,"headpic");
//            level=getInt(rootJson,"level");
//            name=getString(rootJson,"name");
//            userId=getString(rootJson,"userId");
//            invisibility=getInt(rootJson,"invisibility");
//            JSONArray itemsJson = getArray(rootJson, "goodsItems");
//            if (itemsJson != null) {
//                int num = itemsJson.length();
//                mUserConsumeList = new ArrayList<>();
//                UserConsumeBean itemBean;
//                for (int i = 0; i < num; i++) {
//                    itemBean = new UserConsumeBean(getJSONObject(itemsJson, i));
//                    mUserConsumeList.add(itemBean);
//                }
//            }
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public ArrayList<UserConsumeBean> getmUserConsumeList() {
            return mUserConsumeList;
        }

        public void setmUserConsumeList(ArrayList<UserConsumeBean> mUserConsumeList) {
            this.mUserConsumeList = mUserConsumeList;
        }

        public int getInvisibility() {
            return invisibility;
        }

        public void setInvisibility(int invisibility) {
            this.invisibility = invisibility;
        }

        @Override
        public String toString() {
            return "UserContributionBean{" +
                    "amount='" + amount + '\'' +
                    ", headpic='" + headpic + '\'' +
                    ", level=" + level +
                    ", name='" + name + '\'' +
                    ", userId='" + userId + '\'' +
                    ", invisibility=" + invisibility +
                    ", mUserConsumeList=" + mUserConsumeList +
                    '}';
        }
    }

    public static class UserConsumeBean extends BaseObservable {
        private long amount;
        private String goodsCount;
        private int goodsId;

        private String giftName;  //数据库查找
        private String giftUrl;  //数据库查找
        private boolean selected;

        public UserConsumeBean(JSONObject rootJson) {
            super();
//            amount=getLong(rootJson,"amount");
//            goodsCount=getString(rootJson,"goodsCount");
//            goodsId=getInt(rootJson,"goodsId");
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public String getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(String goodsCount) {
            this.goodsCount = goodsCount;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }


        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getGiftUrl() {
            return giftUrl;
        }

        public void setGiftUrl(String giftUrl) {
            this.giftUrl = giftUrl;
        }

        @Bindable
        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
            notifyPropertyChanged(BR.selected);
        }

        @Override
        public String toString() {
            return "UserConsumeBean{" +
                    "amount='" + amount + '\'' +
                    ", goodsCount=" + goodsCount +
                    ", goodsId=" + goodsId +
                    ", giftName='" + giftName + '\'' +
                    ", giftUrl='" + giftUrl + '\'' +
                    ", selected=" + selected +
                    '}';
        }
    }

}
