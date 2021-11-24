package com.wning.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wing.android.R;
import com.wning.demo.net.okhttp.OkHttpActivity;
import com.wning.demo.net.retrofit.RetrofitActivity;
import com.wning.demo.net.volley.VolleyActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NetworkFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button volley;
    private Button okhttp;
    private Button retrofit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_network,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        volley=view.findViewById(R.id.volley);
        volley.setOnClickListener(this);

        okhttp=view.findViewById(R.id.okhttp);
        okhttp.setOnClickListener(this);

        retrofit=view.findViewById(R.id.retrofit);
        retrofit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.volley:
                Intent intent = new Intent();
                intent.setClass(getActivity(), VolleyActivity.class);
                startActivity(intent);
                break;
            case R.id.okhttp:
                 intent = new Intent();
                intent.setClass(getActivity(), OkHttpActivity.class);
                startActivity(intent);
                break;
            case R.id.retrofit:
                 intent = new Intent();
                intent.setClass(getActivity(), RetrofitActivity.class);
                startActivity(intent);
                break;
        }
    }
}
