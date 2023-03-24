package com.wning.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wing.android.R;
import com.wing.android.mvvm.basicsample.ui.MvvmBasicSampleActivity;
import com.wning.demo.architecture.DataBindingActivity;
import com.wning.demo.arouter.ARouterEntranceActivity;
import com.wning.demo.producer.ProducerActivity;
import com.wning.demo.rxjava.RxJava2Activity;

public class ArchitectureFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button rxjava;
    private Button mvp;
    private Button mvvm;
    private Button frescoProducer;
    private Button dataBinding;
    private Button aRouter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_architecture,container,false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rxjava=view.findViewById(R.id.rxjava);
        rxjava.setOnClickListener(this);

        mvp=view.findViewById(R.id.mvp);
        mvp.setOnClickListener(this);

        mvvm=view.findViewById(R.id.mvvm);
        mvvm.setOnClickListener(this);

        frescoProducer=view.findViewById(R.id.frescoProducer);
        frescoProducer.setOnClickListener(this);

        dataBinding=view.findViewById(R.id.dataBinding);
        dataBinding.setOnClickListener(this);

        aRouter=view.findViewById(R.id.aRouter);
        aRouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rxjava:
                Intent intent = new Intent();
                intent.setClass(getActivity(), RxJava2Activity.class);
                startActivity(intent);
                break;
            case R.id.mvp:
                intent = new Intent();
                intent.setClass(getActivity(), RxJava2Activity.class);
                startActivity(intent);
                break;
            case R.id.mvvm:
                intent = new Intent();
                intent.setClass(getActivity(), MvvmBasicSampleActivity.class);
                startActivity(intent);
                break;
            case R.id.frescoProducer:
                intent = new Intent();
                intent.setClass(getActivity(), ProducerActivity.class);
                startActivity(intent);
                break;
            case R.id.dataBinding:
                intent = new Intent();
                intent.setClass(getActivity(), DataBindingActivity.class);
                startActivity(intent);
                break;
            case R.id.aRouter:
                intent = new Intent();
                intent.setClass(getActivity(), ARouterEntranceActivity.class);
                startActivity(intent);
                break;
        }
    }
}
