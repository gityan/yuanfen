package com.example.yuanfen;

import javax.crypto.spec.IvParameterSpec;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button buttonMatch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//随机分配恋人
		buttonMatch = (Button)findViewById(R.id.bButton1);
		buttonMatch.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//点击进入随机分配恋人的Activity
		if(v.getId()==buttonMatch.getId())
		{
//			Toast.makeText(getApplicationContext(), "Match", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();	  
       		intent.setClass(MainActivity.this, MatchActivity.class);	
  			this.startActivity(intent);
		
		}
		
	}

}
