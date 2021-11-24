package com.wning.demo.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.wing.android.R;
import com.wning.demo.customview.view.XfermodeRoundImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class XfermodeFragment extends Fragment {

    private XfermodeRoundImageView riv1;
    private ImageView iv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_xfermode,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        riv1=view.findViewById(R.id.riv1);
        iv=view.findViewById(R.id.iv);

        riv1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                riv1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Bitmap shareBitmap = Bitmap.createBitmap(riv1.getMeasuredWidth(),
                        riv1.getMeasuredHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(shareBitmap);
                riv1.draw(c);

                iv.setImageBitmap(shareBitmap);
            }
        });



    }
}
