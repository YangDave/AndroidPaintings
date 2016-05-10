package com.vgpt.androidpaintings.compoent.widget;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.compoent.activity.auction.AuctionPaintingFragmentActivity;
import com.vgpt.androidpaintings.entity.AuctionItem;
import com.vgpt.androidpaintings.utils.LogUtils;

public class AuctionGalleryItem  implements OnClickListener {
	
	private Context c;
	private ImageView imageView;
	
	private RelativeLayout container;
	private String category = "all";
	private int position = 0;
	
	private TextView uploaderText,nameText,categoryText,remainTimeText,current_priceText,priceTimesText;
	
	public AuctionGalleryItem(Context c){
		this.c=c;
		
        this.container=(RelativeLayout) LayoutInflater.from(this.c).inflate(R.layout.auction_gallery_item, null);
		
		this.imageView=(ImageView)  this.container.findViewById(R.id.gallery_image);
		
		uploaderText=(TextView) this.container.findViewById(R.id.uploaderText);
		
		nameText = (TextView) this.container.findViewById(R.id.nameText);
		
		categoryText = (TextView)this.container.findViewById(R.id.category);
		
		remainTimeText = (TextView)this.container.findViewById(R.id.remainTimeText);
		
		current_priceText = (TextView)this.container.findViewById(R.id.currentPriceText);
		
		priceTimesText = (TextView)this.container.findViewById(R.id.auctiontimes);
		
		this.container.setClickable(true);
		this.container.setOnClickListener(this);
		
		
		
	}
	
	public void setHeight(int height){
		
		LayoutParams para = container.getLayoutParams();
		
		para.height = height;
		
		LogUtils.v("=========="+height+"===========");
		
		container.setLayoutParams(para);
		
		
	}
	
    
    public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setCategory(String category){
    	
    	int cateId=category.equals("gongbi")?R.string.gongbi:category.equals("xieyi")?R.string.xieyi:
    		category.equals("xihua")?R.string.xihua:category.equals("shufa")?R.string.shufa:R.string.others;
    	this.category = category;
    	
    	categoryText.setText(cateId);
    	
    }
    
    public String getCategory(){
    	return category;
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
	
	public void setRemainTime(int mins){
		int hours = mins/60;
		int minutes = mins%60;
		remainTimeText.setText(hours+"时"+minutes+"分");
	}
	
	public void setPriceTimes(int times){
		
		priceTimesText.setText(times+"人出价");
	}
	
	public void setCurrentPrice(int price){
		current_priceText.setText(price+"");
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
		
		Intent intent =new Intent(c,AuctionPaintingFragmentActivity.class);
		
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String, Object>) this.getTag(R.string.image_map);
		
		Bundle data = new Bundle();
		
		AuctionItem ai = new AuctionItem();
		
		ai.setAttribute(map);
		
		data.putSerializable("auctionItem", ai);
		intent.putExtra("position", position);
		
		intent.putExtras(data);
		
		c.startActivity(intent);
		
		
		
	}
	
	public int getTop(){
		return this.container.getTop();
	}
	
	public int getBottom(){
		return this.container.getBottom();
	}

}
