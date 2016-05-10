package com.vgpt.androidpaintings.compoent.widget;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.biz.GetUploadTimeToNow;
import com.vgpt.androidpaintings.compoent.activity.paintings.ImageViewPagerActivity;
import com.vgpt.androidpaintings.utils.LogUtils;

public class GalleryItem  implements OnClickListener {

	protected Context c;
	protected ImageView imageView,photoView;

	protected RelativeLayout container;

	protected TextView uploaderText,nameText,focusText,categoryText,uploadTimeText;
	
	protected int position ;
	
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
		LogUtils.v("===position====="+this.position);
	}

	public GalleryItem(Context c){
		this.c=c;

		this.container=(RelativeLayout) LayoutInflater.from(this.c).inflate(R.layout.gallery_item_layout, null);

		this.imageView=(ImageView)  this.container.findViewById(R.id.gallery_image);

		uploaderText=(TextView) this.container.findViewById(R.id.uploaderText);

		nameText = (TextView) this.container.findViewById(R.id.nameText);

		focusText = (TextView)this.container.findViewById(R.id.focus);

		categoryText = (TextView)this.container.findViewById(R.id.category);

		uploadTimeText = (TextView)this.container.findViewById(R.id.uploadTime);

		photoView = (ImageView)this.container.findViewById(R.id.headPhoto);

		this.container.setClickable(true);
		this.container.setOnClickListener(this);


	}

	public void setHeight(int height){

		LayoutParams para = container.getLayoutParams();

		para.height = height;

		LogUtils.v("=========="+height+"===========");

		container.setLayoutParams(para);


	}


	public Bitmap getImageFromAssetsFile(String fileName)  
	{  
		Bitmap image = null;  
		AssetManager am = c.getResources().getAssets();  
		try  
		{  
			InputStream is = am.open(fileName);  
			image = BitmapFactory.decodeStream(is);  
			is.close();  
		}  
		catch (IOException e)  
		{  
			e.printStackTrace();  
		}  
		return image;

	}



	public ImageView getPhotoView() {
		return photoView;
	}



	public void setPhotoView(ImageView photoView) {
		this.photoView = photoView;
	}

	public void setMoods(int recommendation,int count_read,int count_comment){

		focusText.setText(count_read+"人气/"+count_comment+"评论/"+recommendation+"推荐");
	}

	public void setCategory(String category){

		int cateId=category.equals("gongbi")?R.string.gongbi:category.equals("xieyi")?R.string.xieyi:
			category.equals("xihua")?R.string.xihua:category.equals("shufa")?R.string.shufa:R.string.others;

		categoryText.setText(cateId);

	}

	public void setUploadTime(String uploadDateStr){


		GetUploadTimeToNow guttn = new GetUploadTimeToNow();
		Long quot=guttn.getTime(uploadDateStr);
		quot=quot/1000/60/60/24;
		if(quot==Long.valueOf(0)){
			uploadTimeText.setText("一天内");
		}
		else{
			uploadTimeText.setText(quot+"天前");
		}



	}



	public ImageView getImageView(){
		return this.imageView;
	}



	public void setImage(Bitmap bitmap){

		this.imageView.setImageBitmap(bitmap);

	}

	public void setUploaderText(String str){
		uploaderText.setText(str);
	}

	public void setNameText(String str){
		nameText.setText(str);
	}

	public void setTag(int key, Object object) {

		imageView.setTag(key, object);

	}

	public Object getTag(int key){
		return  imageView.getTag(key);
	}



	public RelativeLayout getContainer() {
		return container;
	}

	@Override
	public void onClick(View v) {



		Intent intent =new Intent(c,ImageViewPagerActivity.class);

		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String, Object>) this.getTag(R.string.image_map);
		String category = this.getTag(R.string.orderOfCategory).toString();

		intent.putExtra("type", "paintings");
		intent.putExtra("position",this.position);
		LogUtils.v("=======position ================="+this.position);

		c.startActivity(intent);

	}

	public int getTop(){

		return this.container.getTop();
	}

	public int getBottom(){
		return this.container.getBottom();
	}
}
