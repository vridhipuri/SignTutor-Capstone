package com.example.imagepro;

/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		android_large___1
	 *	@date 		Friday 01st of December 2023 09:58:40 AM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.imagepro.R;

	public class android_large___1_activity extends Activity {

	
	private View _bg__android_large___1_ek2;
	private ImageView signtutor_modified_1;
	private ImageView rectangle_2;
	private ImageView rectangle_3;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_large___1);

		
		_bg__android_large___1_ek2 = (View) findViewById(R.id._bg__android_large___1_ek2);
		signtutor_modified_1 = (ImageView) findViewById(R.id.signtutor_modified_1);
		rectangle_2 = (ImageView) findViewById(R.id.rectangle_2);
		rectangle_3 = (ImageView) findViewById(R.id.rectangle_3);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent=new Intent(getApplicationContext(),register.class);
				startActivity(intent);
				finish();
			}
		},3000);
	}
}
	
	