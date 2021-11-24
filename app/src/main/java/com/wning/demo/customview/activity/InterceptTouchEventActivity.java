package com.wning.demo.customview.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.guagua.modules.utils.LogUtils;
import com.wing.android.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InterceptTouchEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercept_touch_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setOnClickListener((view)->{

        });

        new MyTask().execute(1f);

    }

    private static class MyTask extends AsyncTask<Float,Integer,String>{
        private static final String TAG = "MyTask";

        @Override
        protected void onPreExecute() {
            LogUtils.i(TAG,"onPreExecute");
        }

        @Override
        protected String doInBackground(Float... f) {
            publishProgress(1);
            return "result";
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

}
