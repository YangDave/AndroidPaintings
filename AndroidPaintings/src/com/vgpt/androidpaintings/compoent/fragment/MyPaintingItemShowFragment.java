package com.vgpt.androidpaintings.compoent.fragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.compoent.activity.paintings.CommentPageActivity;
import com.vgpt.androidpaintings.compoent.fragment.ImageFragment.LoadBitmapTask;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.MyPaintingItem;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.LoadBitmapAsyTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class MyPaintingItemShowFragment extends Fragment{

	int pic_id;
	int on_aucting;
	int user_id;
	TextView picNameText,categoryText,authorText,sizeText,
	createDateText,uploadDateText,renzhengText,introductionText,priceText;
	Button setAuctionBt,viewCommentBt;
	ImageView imageView;

	public static MyPaintingItemShowFragment newInstance(MyPaintingItem item){

		MyPaintingItemShowFragment fragment = new MyPaintingItemShowFragment();
		Bundle args = new Bundle();
		args.putSerializable("item", item);
		fragment.setArguments(args);
		return fragment;
	}

	public MyPaintingItemShowFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mypainting_item_show, container,false);
		MyApplication ma = (MyApplication)getActivity().getApplicationContext();
		user_id = ma.getUser_id();

		initView(rootView);
		MyPaintingItem item = (MyPaintingItem)getArguments().getSerializable("item");
		pic_id = item.getPic_id();
		on_aucting = item.getOn_auction();
		
		if(on_aucting > 0){
			setAuctionBt.setText("正在拍卖中");
			setAuctionBt.setClickable(false);
		}

		getMyPiantingItem(pic_id);
		
		initImageView();


		
		addListener();

		return rootView;
	}

	@SuppressWarnings("unchecked")
	private void initImageView() {

		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int  width = metrics.widthPixels;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pic_id", pic_id);
		LoadBitmapAsyTaskUtil task = new LoadBitmapAsyTaskUtil(getActivity(),width) {
			
			@Override
			public void postExecute(Bitmap bitmap, Map<String, Object> map) {
				
				float ratio = bitmap.getWidth()/(width*1.0f);
				Matrix matrix = new Matrix();
				matrix.postScale(1/ratio, 1/ratio);
				Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				imageView.setImageBitmap(resizeBmp);
				
			}
		};
		task.execute(map);
	}

	private void addListener() {
		
		setAuctionBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showSetAuctionDialog();
			}
		});
		
		viewCommentBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent comIntent=new Intent(getActivity(),CommentPageActivity.class);
				comIntent.putExtra("pic_id", pic_id);
				comIntent.putExtra("user_id", user_id);
				startActivity(comIntent);
				
			}
		});
		
	}
	
	public void showSetAuctionDialog() {
		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setTitle("发起拍卖");
		View view=getActivity().getLayoutInflater().inflate(R.layout.set_auction_dialog, null);
		final EditText et=(EditText)view.findViewById(R.id.set_price);
		builder.setView(view);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				priceText.setText("当前价格（￥）："+et.getText());
				
				Calendar c=Calendar.getInstance();
				int year=c.get(Calendar.YEAR);
				int month=c.get(Calendar.MONTH);
				int day=c.get(Calendar.DAY_OF_MONTH);
				String date=year+"-"+month+"-"+day;
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("pic_id", pic_id);
				map.put("start_price", Integer.parseInt(et.getText().toString()));
				map.put("begin", date);
				setAuction(map);
				Toast.makeText(getActivity(), date, Toast.LENGTH_LONG).show();
				
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}
	/**
	 * 
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	private void setAuction(Map<String,Object> map){
		AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Painting.SET_AUCTION, new OnPostInterface() {
			
			@Override
			public void code_1(JSONObject json) {

				JSONObject result = json.optJSONObject("result");
				if(result.optBoolean("success")){
					Toast.makeText(getActivity(), "发起拍卖成功", Toast.LENGTH_LONG).show();
					priceText.setVisibility(View.VISIBLE);
					setAuctionBt.setClickable(false);
					setAuctionBt.setText("正在拍卖中");
				}else{
					Toast.makeText(getActivity(), "发起拍卖失败", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		atu.execute(map);
	}
	
	

	@SuppressWarnings("unchecked")
	private void getMyPiantingItem(int pic_id) {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id",user_id);
		map.put("pic_id", pic_id);

		AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Painting.PAINTING_SHOW, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {
				JSONObject result = json.optJSONObject("result");
				if(result != null){

					setText(result);

				}

			}

		});

		atu.execute(map);

	}
	private void setText(JSONObject result) {
		authorText.setText(result.optString("author"));
		uploadDateText.setText(result.optString("date_on"));
		createDateText.setText(result.optString("date_creation"));
		introductionText.setText(result.optString("introduction"));
		sizeText.setText("高-"+result.optInt("height")+" 宽-"+result.optInt("width"));
		picNameText.setText(result.optString("name"));
		renzhengText.setText(result.optBoolean("identification")?"是":"否");
		categoryText.setText(Constant.categoryMap.get(result.optString("category")));
	}

	private void initView(View v) {

		imageView = (ImageView)v.findViewById(R.id.myImage);
		viewCommentBt=(Button)v.findViewById(R.id.showcomment);
		categoryText = (TextView)v.findViewById(R.id.category);
		authorText=(TextView)v.findViewById(R.id.author);
		uploadDateText=(TextView)v.findViewById(R.id.date_on);
		createDateText=(TextView)v.findViewById(R.id.date_creation);
		sizeText=(TextView)v.findViewById(R.id.size);
		introductionText=(TextView)v.findViewById(R.id.introduction);
		renzhengText=(TextView)v.findViewById(R.id.identification);
		picNameText=(TextView)v.findViewById(R.id.pic_name);
		setAuctionBt=(Button)v.findViewById(R.id.set_auction);
		priceText=(TextView)v.findViewById(R.id.current_price);

	}

}
