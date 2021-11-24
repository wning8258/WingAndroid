package com.guagua.anim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.guagua.anim.widget.QuotaGiftView;
import com.wing.android.R;

import java.io.IOException;

public class QuotaGiftActivity extends AppCompatActivity {

	private FrameLayout giftContainer;
	private QuotaGiftView repeatGift;
	private Bitmap gift;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repeat_gift);
		giftContainer = (FrameLayout) findViewById(R.id.act_gift_container);
		try {
			gift = BitmapFactory.decodeStream(getAssets().open("gift.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onClick(View view){
		giftContainer.removeAllViews();
		repeatGift = new QuotaGiftView(this, gift, 299);
		giftContainer.addView(repeatGift);
		repeatGift.start();
	}
	
}
