package com.wning.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.Wing;
import com.guagua.modules.utils.Utils;
import com.wing.android.R;
import com.wning.demo.customview.activity.ChoreographerActivity;
import com.wning.demo.customview.activity.CoordinatorLayoutActivity;
import com.wning.demo.customview.activity.InterceptTouchEventActivity;
import com.wning.demo.customview.activity.LooperActivity;
import com.wning.demo.matrix.MatrixActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

@Wing
public class MainActivity extends AppCompatActivity {



    ExpandableListView listView;

    private ExpandableAdapter adapter;

    private ArrayList<DataList> dataItems;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_root_layout);

        listView=findViewById(R.id.list);
        toolbar=findViewById(R.id.toolbar);

        initData();
        initRecyclerView();

        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {

        toolbar.setTitle("MainActivity");

        dataItems=new ArrayList<>();

        ArrayList<DataItem> items=new ArrayList<>();
        items.add(new DataItem().setTitle("CoordinatorLayout").setClazz(CoordinatorLayoutActivity.class));
        items.add(new DataItem().setTitle("InterceptTouchEvent").setClazz(InterceptTouchEventActivity.class));

        dataItems.add(new DataList().setTitle("自定义view").setList(items));

        items=new ArrayList<>();
        items.add(new DataItem().setTitle("Matrix").setClazz(MatrixActivity.class));
        items.add(new DataItem().setTitle("子线程Looper").setClazz(LooperActivity.class));
        items.add(new DataItem().setTitle("Choreographer").setClazz(ChoreographerActivity.class));

        dataItems.add(new DataList().setTitle("动画").setList(items));

    }

    private void initRecyclerView() {

        adapter=new ExpandableAdapter();

        listView.setAdapter(adapter);

       listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
           @Override
           public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               startActivity(new Intent(MainActivity.this,dataItems.get(groupPosition).getList().get(childPosition).getClazz()));

               return true;
           }
       });


    }

    private class ExpandableAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return dataItems.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return dataItems.get(groupPosition).getList().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return dataItems.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return dataItems.get(groupPosition).getList().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_view_item, parent, false);
            TextView btn = (TextView) view.findViewById(R.id.btn);
            ViewGroup.LayoutParams lp=btn.getLayoutParams();
            lp.height=Utils.dip2px(getApplicationContext(),60);
            btn.setLayoutParams(lp);
            btn.setText(dataItems.get(groupPosition).getTitle());
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_view_second_item, parent, false);
            TextView btn = (TextView) view.findViewById(R.id.btn);
            TextView content = (TextView) view.findViewById(R.id.content);
            btn.setText(dataItems.get(groupPosition).getList().get(childPosition).getTitle());
            content.setText("("+dataItems.get(groupPosition).getList().get(childPosition).getClazz().getSimpleName()+".java)");
            content.setVisibility(View.VISIBLE);
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
