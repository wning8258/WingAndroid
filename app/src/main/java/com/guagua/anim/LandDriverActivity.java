package com.guagua.anim;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.guagua.anim.widget.LandDriverView;
import com.wing.android.R;

public class LandDriverActivity extends AppCompatActivity {

	private FrameLayout carContainer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_land_driver);
		carContainer = (FrameLayout) findViewById(R.id.act_land_driver_container);
	}
	
	public void onClick(View view){
		Button button = (Button) view;
		carContainer.removeAllViews();
		if(button.getText().toString().equals("crane")){
			LandDriverView land = new LandDriverView(this, LandDriverView.LAND_CRANE);
			carContainer.addView(land);
			land.start();
		}else if(button.getText().toString().equals("aodi")){
			LandDriverView land = new LandDriverView(this, LandDriverView.LAND_CHERYQQ);
			carContainer.addView(land);
			land.start();
		}else if(button.getText().toString().equals("tricycle")){
			LandDriverView land = new LandDriverView(this, LandDriverView.LAND_TRICYCLE);
			carContainer.addView(land);
			land.start();
		}
	}
	
}
