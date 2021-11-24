package com.guagua.qiqi.ui.room;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.guagua.modules.utils.LogUtils;
import com.guagua.qiqi.widget.QiQiImageView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.view.ViewHelper;
import com.wing.android.R;

import java.util.ArrayList;


/**
 * Created by wning on 2016/10/18.
 */

public class GameCenterView extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "GameCenterView";
    private static final String HTTP_HEAD = "http://";
    private static final String HTTPS_HEAD = "https://";

    public static final String ID_SEPARATOR = "<|>";
    private Context mContext;

    private View view;
    private GameCenterBgView mBgView;
    public ListView mListView;
    private GameAdapter mGameAdapter;
    public ImageView mIcon;

    private ArrayList<GameInfo> games;

    //private WebCmdParser mWebCmdParser;

    private static final int MAX_VISIBLE=5; //listview不显示滚动条时,最多显示5个

    public int height=0;  //总高度

    private boolean isAnimRunning =false;

    private long start,end;

    public GameCenterView(Context context) {
        super(context);
        init(context);
    }

    public GameCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        mContext=context;
//        mWebCmdParser=new WebCmdParser();
//        mWebCmdParser.setWebCmdHandler(new DefaultWebCmdHandler(context));
        view = View.inflate(mContext, R.layout.qiqi_room_game_center, this);
        mIcon = (ImageView) view.findViewById(R.id.entrance);
        mIcon.setOnClickListener(this);
        mBgView = (GameCenterBgView) view.findViewById(R.id.bg);
        mListView = (ListView) view.findViewById(R.id.game_center_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(mContext, "position : "+position, Toast.LENGTH_SHORT).show();
            }
        });
        ViewHelper.setScaleX(mBgView,0f);
        ViewHelper.setScaleY(mBgView,0f);


        ArrayList<GameInfo> games=new ArrayList<GameInfo>();
        games.add(new GameInfo());
        games.add(new GameInfo());
        games.add(new GameInfo());
        games.add(new GameInfo());
        games.add(new GameInfo());

        setData(games);

    }




    public void setData(ArrayList<GameInfo> games) {
        this.games=games;
        mGameAdapter = new GameAdapter(games);
        mListView.setAdapter(mGameAdapter);


        int totalHeight = 0;
        for (int i = 0; i < mGameAdapter.getCount(); i++) {
            View listItem = mGameAdapter.getView(i, null, mListView);
            listItem.measure(0, 0);
            if(i<=MAX_VISIBLE-1) {
                totalHeight += listItem.getMeasuredHeight();
            }
        }


        LayoutParams params = (LayoutParams) mListView.getLayoutParams();
        int count= mGameAdapter.getCount()>MAX_VISIBLE?MAX_VISIBLE:mGameAdapter.getCount();

        params.height = totalHeight + (mListView.getDividerHeight() * (count-1))
                + mListView.getPaddingTop()+mListView.getPaddingBottom()+params.topMargin+params.bottomMargin;
        mListView.setLayoutParams(params);
        height=params.height;
        params= (LayoutParams) mIcon.getLayoutParams();
        height+=params.height+params.topMargin+params.bottomMargin+mIcon.getPaddingTop()+mIcon.getPaddingBottom();
        params= (LayoutParams) mBgView.getLayoutParams();
        params.height=height;
        mBgView.setLayoutParams(params);
    }

    public boolean isListShowing(){
        return mListView.isShown();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.entrance:
                LogUtils.i(TAG,"isListShowing() :"+isListShowing()+" isAnimRunning :"+isAnimRunning);
                if(isAnimRunning)
                    return;
                if(!isListShowing()) {
                    showList();
                }else{
                    hideList();
                }
                break;
            default:
                break;
        }
    }


    public void hideList() {

        if(!isListShowing()||isAnimRunning)
            return;

        isAnimRunning=true;

        ScaleAnimation scaleAnim=new ScaleAnimation(1f, 0.01f, 1f,0.01f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setStartOffset(200);
        scaleAnim.setDuration(100);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtils.i(TAG,"hideList onAnimationEnd");
                mListView.setVisibility(View.GONE);
                isAnimRunning=false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        for (int i = 0; i < mListView.getChildCount(); i++) {
            View view=mListView.getChildAt(i);
            if(view!=null){
                view.startAnimation(scaleAnim);
            }
        }

        PropertyValuesHolder pvh1= PropertyValuesHolder.ofFloat("scaleX",1f,0f);
        PropertyValuesHolder pvh2= PropertyValuesHolder.ofFloat("scaleY",1f,0f);
        PropertyValuesHolder pvh3= PropertyValuesHolder.ofFloat("alpha",1f,0f);
        ObjectAnimator anim= ObjectAnimator.ofPropertyValuesHolder(mBgView,pvh1,pvh2,pvh3).setDuration(150);
        anim.start();
    }

    private void showList() {


        if(isListShowing()||isAnimRunning)
            return;

        isAnimRunning=true;

        ViewHelper.setScaleX(mBgView,1f);
        ViewHelper.setScaleY(mBgView,1f);
        ViewHelper.setAlpha(mBgView,1f);
        mBgView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.VISIBLE);

        SpringSystem springSystem = SpringSystem.create();
        // Add a spring to the system.
        Spring spring = springSystem.createSpring();
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100,5));
        // Add a listener to observe the motion of the spring.
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.

                end=System.currentTimeMillis();

                LogUtils.i(TAG,"onSpringUpdate delay :"+(end-start));

                start=System.currentTimeMillis();

                float value = (float) spring.getCurrentValue();
                ViewHelper.setScaleX(mBgView,value);
                ViewHelper.setScaleY(mBgView,value);

            }

            @Override
            public void onSpringAtRest(Spring spring) {
                LogUtils.i(TAG,"onSpringAtRest :");
                isAnimRunning=false;
            }
        });

        // Set the spring in motion; moving from 0 to 1
        spring.setEndValue(1);
        start=System.currentTimeMillis();

        ScaleAnimation scaleAnim=new ScaleAnimation(0.01f, 1f, 0.01f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(400);
        for (int i = 0; i < mListView.getChildCount(); i++) {
            View view=mListView.getChildAt(i);
            if(view!=null){
                view.startAnimation(scaleAnim);
            }
        }
    }

    private class GameAdapter extends BaseAdapter{

        private ArrayList<GameInfo> games;

        public GameAdapter(ArrayList<GameInfo> games) {
            this.games = games;
        }

        @Override
        public int getCount() {
            return games.size();
        }

        @Override
        public Object getItem(int position) {
            return games.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            GameInfo info = games.get(position);
            View view=LayoutInflater.from(mContext).inflate(R.layout.qiqi_room_game_center_item,parent,false);
            QiQiImageView iv= (QiQiImageView) view.findViewById(R.id.icon);
            if(!TextUtils.isEmpty(info.icon)){
                iv.setUrl(Uri.parse(info.icon));
            }
            return view;

        }
    }



}