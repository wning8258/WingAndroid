package com.wing.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.wing.android.aidl.BookManagerActivity;
import com.wing.android.bindpool.BinderPoolActivity;
import com.wing.android.databinding.ActivityMainBinding;
import com.wing.android.messenger.MessengerActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        //view binding
        setContentView(view);
        setSupportActionBar(mainBinding.toolbar);

        mainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mainBinding.messengerBtn.setOnClickListener(this);
        mainBinding.aidlBookManagerBtn.setOnClickListener(this);
        mainBinding.binderPoolBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messenger_btn:
                startActivity(new Intent(MainActivity.this, MessengerActivity.class));
                break;
            case R.id.aidl_book_manager_btn:
                startActivity(new Intent(MainActivity.this, BookManagerActivity.class));
                break;
            case R.id.binder_pool_btn:
                startActivity(new Intent(MainActivity.this, BinderPoolActivity.class));
                break;
        }
    }
}