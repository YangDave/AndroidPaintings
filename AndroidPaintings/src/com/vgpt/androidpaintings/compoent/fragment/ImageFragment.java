package com.vgpt.androidpaintings.compoent.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.biz.TimeBiz;
import com.vgpt.androidpaintings.compoent.activity.paintings.ImageViewPagerActivity;
import com.vgpt.androidpaintings.compoent.activity.paintings.PictureChangeSizeShowActivity;
import com.vgpt.androidpaintings.compoent.activity.user.PersonInfoActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.PaintingItem;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.LoadBitmapAsyTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImageFragment extends Fragment {

	ImageView imageView,photoView;
	TextView mNameText,uploaderText,categoryText,countText,commentText,collectText,recommendText;
	TextView date_onText;
	TextView introductionText;
	Button focusButton;
	LinearLayout mLinearLayout;

	LinearLayout commentList;


	int width;
	String  category;
	PaintingItem pi = null;
	SharedPreferences userPre;
	int pic_id = 0;
	int user_id = ImageViewPagerActivity.user_id;
	int uploader_id = 0;

	int page = 1;

	Boolean isWorking = false;
	Bitmap bm = null;
	
	private boolean isCollectPage = ImageViewPagerActivity.isMyCollectPage;

	final static int SIZE = 10;

	final String comment="intent.CommentPage";
	final static String PAITINGITEM = "paintingItem";

	List<PaintingItem> paintingItems = new ArrayList<PaintingItem>();
	@SuppressLint("UseSparseArrays")
	Map<Integer, JSONObject> jsonMap = new HashMap<Integer, JSONObject>();

	ArrayList<Integer> pic_idList = new ArrayList<Integer>();
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ImageFragment newInstance(PaintingItem pi) {
		ImageFragment fragment = new ImageFragment();
		Bundle args = new Bundle();
		args.putSerializable(PAITINGITEM, pi);
		fragment.setArguments(args);
		return fragment;
	}

	public ImageFragment() {
	}



	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}



	@Override
	public void onDetach() {
		super.onDetach();

		commentList.removeAllViews();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_image_viewpager, container,
				false);
		setHasOptionsMenu(true);
		
		commentList = null;
		pi = (PaintingItem)getArguments().get(PAITINGITEM);
		pic_id = pi.getPic_id();
		uploader_id = pi.getUploader_id();

		LogUtils.v("uploader_id = "+uploader_id+ " user_id = "+user_id);
		
		findView(rootView);
		
		addListener();

		initCommentList(pic_id);

		initTextView(pi);

		initImageView(pic_id);

		loadImageInfo();

		initFocusedButton(uploader_id);

		return rootView;
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem item = menu.add(getString(R.string.comment));
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				final AlertDialog.Builder builder=new Builder(getActivity());
				View view=getActivity().getLayoutInflater().inflate(R.layout.comment_edit, null);
				final EditText et=(EditText)view.findViewById(R.id.commenteditText);
				builder.setView(view);
				builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

					@SuppressWarnings("unchecked")
					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						String content=et.getText().toString().replaceAll("(^\\s{1,})|(\\s{1,}$)" , "");
						if(content!=null && !content.equals("")){
							setComment(content);
						}
					}
				});
				builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
				builder.show();
				return false;
			}
		});
		
		MenuItem collectItem = menu.add(getString(R.string.collect));
		
		collectItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		if(isCollectPage){
			collectItem.setTitle("已收藏");
			collectItem.setEnabled(false);
		}
		collectItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean onMenuItemClick(final MenuItem item) {

			setCollect(item);

				return false;
			}
		});
	}


	@SuppressWarnings("unchecked")
	private void initImageView(int pic_id){
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		LogUtils.v("==========="+width+" pic_id"+pic_id);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pic_id", pic_id);
		LoadBitmapTask task = new LoadBitmapTask(getActivity(), width);
		task.execute(map);
	}

	class LoadBitmapTask extends LoadBitmapAsyTaskUtil{

		public LoadBitmapTask(Context context, int width) {
			super(context, width);
		}

		@Override
		public void postExecute(Bitmap t, Map<String, Object> map) {

			float ratio = t.getWidth()/(width*1.0f);

			Matrix matrix = new Matrix();
			matrix.postScale(1/ratio, 1/ratio);
			Bitmap resizeBmp = Bitmap.createBitmap(t, 0, 0,
					t.getWidth(), t.getHeight(), matrix, true);

			imageView.setImageBitmap(resizeBmp);

		}

	}


	private void findView(View v){
		imageView = (ImageView) v.findViewById(R.id.image);

		photoView = (ImageView)  v.findViewById(R.id.headPhoto);

		categoryText = (TextView) v.findViewById(R.id.category);

		countText = (TextView) v.findViewById(R.id.count);

		mNameText = (TextView) v.findViewById(R.id.name);
		uploaderText = (TextView) v.findViewById(R.id.uploader);
		date_onText = (TextView) v.findViewById(R.id.date_on);
		introductionText = (TextView) v.findViewById(R.id.introduction);
		uploaderText = (TextView) v.findViewById(R.id.uploader);
		commentText = (TextView)v.findViewById(R.id.comment);
		collectText = (TextView)v.findViewById(R.id.collect);
		recommendText = (TextView)v.findViewById(R.id.recommendation);
		focusButton = (Button) v.findViewById(R.id.focus);

		mLinearLayout = (LinearLayout)v.findViewById(R.id.linearLayout);

		commentList = (LinearLayout)v.findViewById(R.id.comment_container);

	}

	public void addListener() {


		focusButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {
				Map<String,Object> setfocusmap=new HashMap<String,Object>();
				setfocusmap.put("user_id", user_id);
				setfocusmap.put("focus_id", uploader_id);

				AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Api.SET_FOCUS, new OnPostInterface() {

					@Override
					public void code_1(JSONObject json) {

						JSONObject result = json.optJSONObject("result");
						boolean isSuccess = result.optBoolean("isSuccess");
						if(isSuccess ){
							buttonFocused();
							Toast.makeText(getActivity(), "关注成功", Toast.LENGTH_SHORT).show();
						}
						else{
							Toast.makeText(getActivity(), "关注失败", Toast.LENGTH_SHORT).show();
						}

					}
				});

			}
		});

		uploaderText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if(uploader_id > 0){
					Intent it = new Intent(getActivity(),
							PersonInfoActivity.class);
					it.putExtra("person_id", uploader_id);
					LogUtils.v("uploader_id"+uploader_id+"=========================");
					it.putExtra("user_id",user_id);
					it.putExtra("username", uploaderText.getText().toString());
					startActivity(it);
				}

			}

		});

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						PictureChangeSizeShowActivity.class);
				intent.putExtra("url", Constant.Painting.GET_PICTURE+pic_id);
				startActivity(intent);

			}
		});


		photoView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(getActivity(),
						PictureChangeSizeShowActivity.class);
				intent.putExtra("url", Constant.Api.GET_PHOTO+uploader_id);
				startActivity(intent);

			}
		});


	}

	private void buttonFocused(){
		focusButton.setClickable(false);
		focusButton.setText(R.string.focused);
		focusButton.setBackgroundColor(Color.GRAY);
	}

	@SuppressWarnings("unchecked")
	public void initCommentList(int pic_id) {

		/*
		 * 解决溢出菜单不显示问题
		 */
		try {
			ViewConfiguration mconfig = ViewConfiguration.get(getActivity());
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(mconfig, false);
			}
		} catch (Exception ex) {
		}

		LogUtils.v("=======pic_id "+pic_id+"========");

		refreshCommentList();



	}

	@SuppressWarnings("unchecked")
	private void refreshCommentList(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pic_id", pic_id);
		map.put("page", page);
		map.put("size", SIZE);

		AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Painting.GET_COMMENT, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONArray ret = json.optJSONArray("result");
				if(ret!=null){
					
					page++;
					displayCommentList(ret);

				}
				else if(ret==null){
					Toast.makeText(getActivity(), "暂无评论", Toast.LENGTH_LONG).show();
				}

			}
		});
		atu.execute(map);
	}

	private void displayCommentList(JSONArray ret){
		View commentView = null;
		for(int i=0;i<ret.length();i++){
			final JSONObject jsonItem=ret.optJSONObject(i);

			int uploader_id = jsonItem.optInt("user_id");
			String uploaderName = jsonItem.optString("username");
			String body = jsonItem.optString("body");
			String time = jsonItem.optString("time");
//			commentView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_item, null);
			//通过系统提供的实例获得一个LayoutInflater对象  
			LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
			//第一个参数为xml文件中view的id，第二个参数为此view的父组件，可以为null，android会自动寻找它是否拥有父组件  
			commentView = inflater.inflate(R.layout.comment_item, null);  


			ImageView photo = (ImageView)commentView.findViewById(R.id.comment_headphoto);

			TextView nameText = (TextView)commentView.findViewById(R.id.name);
			TextView commentText = (TextView) commentView
					.findViewById(R.id.comment);
			TextView dateText = (TextView)commentView.findViewById(R.id.date);

			Picasso.with(getActivity()).load(Constant.Api.GET_PHOTO+uploader_id).into(photo);

			nameText.setText(uploaderName);
			nameText.setTag(uploader_id);
			dateText.setText(time);
			commentText.setText(body); 
			photo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(getActivity(),PersonInfoActivity.class);
					intent.putExtra("person_id", jsonItem.optInt("user_id"));
					startActivity(intent);

				}
			});

			commentList.addView(commentView);

			LogUtils.v("=====body"+jsonItem.optString("body")+"==========");

		}

	}
	class ItemViewHolder{
		ImageView photo;
		TextView nameText,commentText,dateText;
	}

	public void initTextView(PaintingItem pi) {

		pic_id = pi.getPic_id();

		LogUtils.v("============"+pic_id+"=============");

		int count_read = pi.getCount_read();
		int count_comment = pi.getCount_comment();
		int  recommendation = pi.getRecommendation();
		countText.setText(count_read+"人气/"+count_comment+"评论/"+recommendation+"推荐");

		commentText.setText("评论  "+count_comment);
		recommendText.setText("推荐  "+recommendation);
		collectText.setText("收藏  "+count_read);

		category = pi.getCategory();
		categoryText.setText(Constant.categoryMap.get(category));

		String date_on = pi.getDate_on();
		date_onText.setText(TimeBiz.getPeriod(date_on));

		String pic_name = pi.getPic_name();
		mNameText.setText(pic_name);

		String uploader = pi.getUploader();
		uploaderText.setText(uploader);


		uploader_id = pi.getUploader_id();
		Picasso.with(getActivity()).load(Constant.Api.GET_PHOTO+uploader_id).into(photoView);


	}
	@SuppressWarnings("unchecked")
	public void loadImageInfo(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pic_id", pic_id);
		map.put("user_id", user_id);
		AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Painting.PAINTING_SHOW, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONObject ret = json.optJSONObject("result");
				jsonMap.put(pic_id, ret);

				openJSONMap(ret);

			}
		});

		atu.execute(map);
	}

	public void openJSONMap(JSONObject ret) {
		String name = ret.optString("name");
		String introduction=ret.optString("introduction");
		String up_name = ret.optString("username");
		uploader_id = ret.optInt("up_actor_id");

		mNameText.setText(name);
		if(introduction!=null && !introduction.isEmpty()){
			introductionText.setText(introduction);
		}
		uploaderText.setText(up_name);

	}

	@SuppressWarnings("unchecked")
	private void initFocusedButton(final int uploader_id){
		if(user_id != uploader_id){

			Map<String,Object> map = new HashMap<String, Object>();
			map.put("user_id",user_id);
			map.put("person_id", uploader_id);
			AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Api.GET_OTHERS_INFO, new OnPostInterface() {

				@Override
				public void code_1(JSONObject json) {

					JSONObject result = json.optJSONObject("result");

					boolean isFocus = result.optBoolean("focus");

					if(isFocus){

						buttonFocused();
					}


				}
			});
			atu.execute(map);

		}else{
			focusButton.setVisibility(View.INVISIBLE);
		}

		focusButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {

				Map<String,Object> map = new HashMap<String, Object>();
				map.put("user_id", user_id);
				map.put("focus_id", uploader_id);

				AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Api.SET_FOCUS, new OnPostInterface() {

					@Override
					public void code_1(JSONObject json) {

						JSONObject result = json.optJSONObject("result");
						if(result.optBoolean("success")){
							Toast.makeText(getActivity(), R.string.focus_success, Toast.LENGTH_LONG).show();
							buttonFocused();
						}else{
							Toast.makeText(getActivity(), R.string.focus_fail, Toast.LENGTH_LONG).show();
						}




					}
				});
				atu.execute(map);

			}
		});

	}

	private void setComment(String content){

		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pic_id", pic_id);
		map.put("user_id", user_id);
		map.put("body", content);
		AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Painting.SET_COMMENT, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONObject ret = json.optJSONObject("result");

				boolean isSuccess=ret.optBoolean("success");
				if(isSuccess){

					page = 1;
					commentList.removeAllViews();
					refreshCommentList();
					Toast.makeText(getActivity(), "发表评论成功", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getActivity(), "发表评论失败", Toast.LENGTH_LONG).show();
				}

			}
		});
		atu.execute(map);
	}
	
	private void setCollect(final MenuItem item){
		
		Map<String,Object> map = new HashMap<String, Object>();

		map.put("user_id", user_id);
		map.put("pic_id", pic_id);

		AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Painting.SET_COL, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONObject ret = json
						.optJSONObject("result");
				boolean successOrNot = ret.optBoolean("success");
				if (successOrNot) {
					item.setTitle("已收藏");
					item.setEnabled(false);
					Toast.makeText(getActivity(), "添加收藏成功",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "收藏失败",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		atu.execute(map);
	}


}