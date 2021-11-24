package com.guagua.anim;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.guagua.anim.widget.FlyDriverView;
import com.guagua.anim.widget.GuaGuaAnimFactory;
import com.guagua.anim.widget.GuaGuaAnimView;
import com.wing.android.R;

public class FlyDriverActivity extends AppCompatActivity {

	private FrameLayout carContainer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fly_driver);
		carContainer = (FrameLayout) findViewById(R.id.act_fly_driver_container);
	}
	
	public void onClick(View view){
		Button button = (Button) view;
		carContainer.removeAllViews();
		if(button.getText().toString().equals("broom")){
//			FlyDriverView broom = new FlyDriverView(this, FlyDriverView.DRIVER_BROOM);
			GuaGuaAnimView broom = GuaGuaAnimFactory.getDriverAnimView(this, FlyDriverView.DRIVER_BROOM);
			carContainer.addView(broom);
			broom.start();
		}else if(button.getText().toString().equals("ufo")){
			FlyDriverView ufo = new FlyDriverView(this, FlyDriverView.DRIVER_UFO);
			carContainer.addView(ufo);
			ufo.start();
		}else if(button.getText().toString().equals("rocket")){
			FlyDriverView rocket = new FlyDriverView(this, FlyDriverView.DRIVER_ROCKET);
			carContainer.addView(rocket);
			rocket.start();
		}
	}
	
}
