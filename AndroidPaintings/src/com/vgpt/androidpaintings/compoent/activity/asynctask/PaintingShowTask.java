package com.vgpt.androidpaintings.compoent.activity.asynctask;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;

public class PaintingShowTask extends AsyncTask<View, Integer, Drawable> {

	private View mView;
	private HashMap<String, SoftReference<Drawable>> imageCache;
	Drawable drawable;

	public PaintingShowTask() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	@Override
	protected Drawable doInBackground(View... params) {

		drawable = null;
		View view = params[0];
		if (view.getTag() != null) {
			if (imageCache.containsKey(view.getTag())) {
				SoftReference<Drawable> cache = imageCache.get(view
						.getTag().toString());
				drawable = cache.get();
				if (drawable != null) {
					return drawable;
				}
			}
			try {
				if (URLUtil.isHttpUrl(view.getTag().toString())) {
					URL url = new URL(view.getTag().toString());
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setDoInput(true);
					conn.connect();
					InputStream stream = conn.getInputStream();
					drawable = Drawable.createFromStream(stream, "src");
					stream.close();
				}

			} catch (Exception e) {
				Log.v("painting", e.getMessage());
				return null;
			}
		}
		this.mView = view;
		return drawable;
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onCancelled(Drawable result) {
		// TODO Auto-generated method stub
		super.onCancelled(result);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(Drawable result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (drawable != null) {
			((ImageView)this.mView).setImageDrawable(result);
//			Bitmap bitmap=BitmapExchangeDrawable.drawableToBitmap(drawable);
//			ImageFileCache imageFileCache=new ImageFileCache();
//			imageFileCache.savedBitmap(bitmap, mView.getTag().toString());

		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
