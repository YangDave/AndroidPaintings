package com.vgpt.androidpaintings.compoent.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.vgpt.androidpaintings.R;

public class OneButtonDialog extends AlertDialog{
	
	protected OneButtonDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void showDialog(Context context,int source,int title, int message){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setIcon(source);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.show();
		
	}

}
