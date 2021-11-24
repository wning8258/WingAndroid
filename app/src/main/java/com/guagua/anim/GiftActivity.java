package com.guagua.anim;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wing.android.R;


public class GiftActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gift);
	}
	
	public void onClick(View view){
		Button button = (Button) view;
		Intent intent = new Intent();
		if(button.getText().toString().equals("sigleGift")){
			intent.setClass(this, SingleGiftActivity.class);
		}else if(button.getText().toString().equals("repeatGift")){
			intent.setClass(this, RepeatGiftActivity.class);
		}else if(button.getText().toString().equals("quotaGift")){
			intent.setClass(this, QuotaGiftActivity.class);
		}
		startActivity(intent);
	}
	
}
