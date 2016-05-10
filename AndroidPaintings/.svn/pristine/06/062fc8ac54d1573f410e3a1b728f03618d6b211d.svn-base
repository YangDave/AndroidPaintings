package com.vgpt.androidpaintings.compoent.widget;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.compoent.activity.paintings.ImageViewPagerActivity;

public class MyCollectGalleryItem extends GalleryItem{

	public MyCollectGalleryItem(Context c) {
		super(c);
	}

	@Override
	public void onClick(View v) {

		Intent intent =new Intent(c,ImageViewPagerActivity.class);

		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String, Object>) this.getTag(R.string.image_map);


		intent.putExtra("type", "mycollect");
		intent.putExtra("position", super.position);

		c.startActivity(intent);
	}

	
}
