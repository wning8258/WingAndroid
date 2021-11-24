package com.wning.demo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.wing.android.R;
import com.wning.demo.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewFragment extends Fragment {

    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;

    private String[] titles={"Xfermode","Bezier","ViewDragHelper","CustomView"};
    private List<Fragment> fragmentList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customview,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewPager=view.findViewById(R.id.viewpager);
        mViewPager.setCanScroll(false);
        mTabLayout=view.findViewById(R.id.tablayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);

        fragmentList.add(new XfermodeFragment());
        fragmentList.add(new BezierFragment());
        fragmentList.add(new ViewDragHelperFragment());
        fragmentList.add(new CustomViewFragment());

        mViewPager.setAdapter(new CustomViewAdapter(getChildFragmentManager()));
    }

    private class CustomViewAdapter extends FragmentPagerAdapter{

        public CustomViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
