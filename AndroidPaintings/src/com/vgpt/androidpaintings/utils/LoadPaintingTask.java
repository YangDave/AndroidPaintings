package com.vgpt.androidpaintings.utils;


import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.interfacepackage.LoadPaintingInterface;

public class LoadPaintingTask extends AsyncTask<String, Integer, Bitmap>{
	
	private LoadPaintingInterface loadPaintingInterface;
	private String url;
	private Context mContext;
	
	public LoadPaintingTask(Context mContext, String url,LoadPaintingInterface loadPaintingInterface){
		
		this.loadPaintingInterface = loadPaintingInterface;
		this.url = url;
		this.mContext = mContext;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bm = null;
		

		try {
			bm = Picasso.with(mContext).load(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bm;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		
		loadPaintingInterface.showImage(result);
	}
	
	

}
