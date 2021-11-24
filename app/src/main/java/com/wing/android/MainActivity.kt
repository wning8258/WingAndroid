package com.wing.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.wing.android.R
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.wing.android.messenger.MessengerActivity
import com.wing.android.aidl.BookManagerActivity
import com.wing.android.bindpool.BinderPoolActivity
import com.wing.android.databinding.ActivityIpcRootLayoutBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mainBinding: ActivityIpcRootLayoutBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityIpcRootLayoutBinding.inflate(layoutInflater)
        val view: View = mainBinding!!.root
        //view binding
        setContentView(view)
        setSupportActionBar(mainBinding!!.toolbar)
        mainBinding!!.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        mainBinding!!.messengerBtn.setOnClickListener(this)
        mainBinding!!.aidlBookManagerBtn.setOnClickListener(this)
        mainBinding!!.binderPoolBtn.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.messenger_btn -> startActivity(Intent(this@MainActivity, MessengerActivity::class.java))
            R.id.aidl_book_manager_btn -> startActivity(Intent(this@MainActivity, BookManagerActivity::class.java))
            R.id.binder_pool_btn -> startActivity(Intent(this@MainActivity, BinderPoolActivity::class.java))
        }
    }
}