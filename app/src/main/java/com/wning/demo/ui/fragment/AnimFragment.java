package com.wning.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.guagua.anim.GiftEffectActivity;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.wing.android.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AnimFragment extends Fragment implements View.OnClickListener {

    private ObjectAnimator colorAnim;
    private TextView tv_argb;
    private Button giftEffect;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_anim,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tv_argb=view.findViewById(R.id.tv_argb);
        colorAnim = ObjectAnimator.ofInt(tv_argb, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_argb.setText(Integer.toHexString((Integer) animation.getAnimatedValue()));
            }
        });
        colorAnim.start();

        giftEffect=view.findViewById(R.id.giftEffect);
        giftEffect.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(colorAnim!=null&&colorAnim.isRunning()){
            colorAnim.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.giftEffect:
                Intent intent = new Intent();
                intent.setClass(getActivity(), GiftEffectActivity.class);
                startActivity(intent);
                break;
        }
    }


}
