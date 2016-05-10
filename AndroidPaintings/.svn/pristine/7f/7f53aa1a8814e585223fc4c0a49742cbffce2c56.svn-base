package com.vgpt.androidpaintings.compoent.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.biz.BitmapChangeSize;
import com.vgpt.androidpaintings.biz.ImageLoader;
import com.vgpt.androidpaintings.biz.JSONToMap;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.PaintingItem;
import com.vgpt.androidpaintings.utils.AsycTaskWithWorkingUtil;
import com.vgpt.androidpaintings.utils.AsycTaskWithWorkingUtil.OnPostWithWorkingInterface;
import com.vgpt.androidpaintings.utils.DeviceUtil;
import com.vgpt.androidpaintings.utils.LoadBitmapAsyTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

/**
 * 自定义的ScrollView，在其中动态地对图片进行添加。
 * 
 * @author guolin
 */
public class MyCollectScrollView extends ScrollView implements OnTouchListener {

	private OnScrollToTopListener onScrollToTop;

	private OnScrollDownListener onScrollDown;


	/**
	 * 每页要加载的图片数量
	 */
	public static final int PAGE_SIZE = 4;

	public static final int IMAGE = 100001;
	public static final int NAMEVIEW = 100002;
	public static final int UPLOADERVIEW = 100003;
	public static final int HEADVIEW = 100004;

	public String category = "all";
	
	public static String picCategory = "all";
	
	/*
	 * 记录当前已加载到第几页
	 */
	private int page = 1;


	/**
	 * 每一列的宽度
	 */
	private int columnWidth;

	/**
	 * 当前第一列的高度
	 */
	private int firstColumnHeight = 0;

	/**
	 * 当前第二列的高度
	 */
	private int secondColumnHeight = 1;

	/**
	 * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
	 */
	private boolean loadOnce;

	/**
	 * 对图片进行管理的工具类
	 */
	private ImageLoader imageLoader;

	/**
	 * 第一列的布局
	 */
	private LinearLayout firstColumn;

	/**
	 * 第二列的布局
	 */
	private LinearLayout secondColumn;


	/**
	 * 记录所有正在下载或等待下载的任务。
	 */
	private Set<LoadBitmapTask> taskCollection;

	/*
	 * 图画的文本信息存储集合
	 */
	private List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();

	/**
	 * MyScrollView下的直接子布局。
	 */
	private static View scrollLayout;

	/**
	 * MyScrollView布局的高度。
	 */
	private static int scrollViewHeight;

	/**
	 * 记录上垂直方向的滚动距离。
	 */
	private static int lastScrollY = -1;

	/**
	 * 记录所有界面上的图片，用以可以随时控制对图片的释放。
	 */

	public static List<GalleryItem> galleryItemList = new ArrayList<GalleryItem>();
	
	public static List<PaintingItem> PaintingColletctedItems = new ArrayList<PaintingItem>();
	
	public static int galleryItemsSize = 0;
	
	private int position = 0;


	/**
	 * 在Handler中进行图片可见性检查的判断，以及加载更多图片的操作。
	 */

	private boolean isWorking = false;
	
	private int user_id = 0;

	Bitmap emptybm = BitmapFactory.decodeResource(getResources(), R.drawable.empty_photo);

	Map<String, Object> map = new HashMap<String, Object>();
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			MyCollectScrollView myScrollView = (MyCollectScrollView) msg.obj;
			int scrollY = myScrollView.getScrollY();
			// 如果当前的滚动位置和上次相同，表示已停止滚动
			if (scrollY == lastScrollY) {
				// 当滚动的最底部，并且当前没有正在下载的任务时，开始加载下一页的图片
				if ((scrollViewHeight + scrollY >= scrollLayout
						.getHeight()) && (taskCollection.isEmpty()) && !isWorking) {
					myScrollView.loadMorePaintings(category);

				}
				myScrollView.checkVisibility();
			} else {
				lastScrollY = scrollY;
				Message message = new Message();
				message.obj = myScrollView;
				// 5毫秒后再次对滚动位置进行判断
				handler.sendMessageDelayed(message, 50);
			}

		};

	};

	/**
	 * MyScrollView的构造函数。
	 * 
	 * @param context
	 * @param attrs
	 */
	public MyCollectScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		imageLoader = ImageLoader.getInstance();
		MyApplication ma = (MyApplication)context.getApplicationContext();
		user_id = ma.getUser_id();
		taskCollection = new HashSet<LoadBitmapTask>();
		setOnTouchListener(this);
	}

	/**
	 * 进行一些关键性的初始化操作，获取MyScrollView的高度，以及得到第一列的宽度值。并在这里开始加载第一页的图片。
	 */
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			scrollViewHeight = getHeight();
			scrollLayout = getChildAt(0);
			firstColumn = (LinearLayout) findViewById(R.id.first_column);
			secondColumn = (LinearLayout) findViewById(R.id.second_column);

			columnWidth = firstColumn.getWidth();

			LogUtils.v("==========height ="+getHeight()+"===="+"columnWidth = "+columnWidth+"===");

			loadOnce = true;
			loadMorePaintings(category);
		}
	}

	/**
	 * 监听用户的触屏事件，如果用户手指离开屏幕则开始进行滚动检测。
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP && !isWorking) {
			LogUtils.v("===========" + "touch is done" + "===================");
			Message message = new Message();
			message.obj = this;
			handler.sendMessageDelayed(message, 5);



		}

		return false;
	}

	/**
	 * 开始加载下一页的图片，每张图片都会开启一个异步线程去下载。
	 */

	@SuppressWarnings("unchecked")
	public void loadMorePaintings(String category) {
		LogUtils.v("==================loadMorePaintings===================");
		if (DeviceUtil.hasSDCard()) {
			map.put("size", PAGE_SIZE);
			map.put("page", page);
			map.put("user_id", user_id);
			map.put("category", category);

			AsycTaskWithWorkingUtil atu = new AsycTaskWithWorkingUtil(getContext(), Constant.Painting.GET_COL, new OnPostWithWorkingInterface() {

				@Override
				public void code_1(JSONObject json) {

					LogUtils.v(" paintingInfo "+json.toString());

					JSONObject ret = json
							.optJSONObject("result");
					JSONArray items = ret.optJSONArray("items");
					JSONObject request = json
							.optJSONObject("request");
					if (items != null) {
						LogUtils.v("============ this is page: "+page);

						page++;
						for (int i = 0; i < items.length(); i++) {

							JSONObject item = items.optJSONObject(i);
							Map<String, Object> map;
							try {
								map = JSONToMap.toMap(item);
								
								LoadBitmapTask task = new LoadBitmapTask(getContext(), columnWidth);
								taskCollection.add(task);
								task.execute(map);
								infoList.add(map);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}

				}

				@Override
				public void finishPost() {
					isWorking = false;

				}
			});
			atu.execute(map);
			isWorking = true;

		} else {
			Toast.makeText(getContext(), "未发现SD卡", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 遍历imageViewList中的每张图片，对图片的可见性进行检查，如果图片已经离开屏幕可见范围，则将图片替换成一张空图。
	 */
	@SuppressWarnings("unchecked")
	public void checkVisibility() {
		for (int i = 0; i < galleryItemList.size(); i++) {
			GalleryItem galleryItem = galleryItemList.get(i);
			int borderBottom = galleryItem.getBottom();
			int borderTop = galleryItem.getTop();

			LogUtils.v("borderBottom "+borderBottom+ " borderTop "+borderTop);

			if (borderBottom + 300 > getScrollY()
					&& borderTop - 300 < getScrollY() + scrollViewHeight) {
				Map<String, Object> map = (Map<String, Object>) galleryItem
						.getTag(R.string.image_map);
				Bitmap bitmap =
						imageLoader.getBitmapFromMemoryCache(Constant.Painting.GET_PICTURE+map.get("pic_id").toString()+".jpeg");
				if (bitmap != null) {

					int height = (Integer)galleryItem.getImageView().getTag(R.string.imageview_height);
					float heightF = (float)height;
					Bitmap resizeBm = BitmapChangeSize.big(bitmap, (float)columnWidth, heightF);
					galleryItem.setImage(resizeBm);
				} else {
					LoadBitmapTask task = new LoadBitmapTask(getContext(), columnWidth);
					task.execute(map);
				}
			} else {

				int height = (Integer)galleryItem.getImageView().getTag(R.string.imageview_height);

				float heightF = (float)height;

				Bitmap resizeBm = BitmapChangeSize.big(emptybm, (float)columnWidth, heightF);

				galleryItem.getImageView().setImageBitmap(resizeBm);
			}
		}
	}

	/**
	 * 异步下载图片的任务。
	 * 
	 * @author guolin
	 */


	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		if (scrollY < 0) {
			if (null != onScrollToTop) {
				onScrollToTop.onScrollTopListener(clampedY);
			}
		}
		if (scrollY > 0) {
			onScrollDown.onScrollDown(true);
		}
	}

	public void setOnScrollToTopLintener(OnScrollToTopListener listener) {
		onScrollToTop = listener;
	}

	public void setOnScrollDownListener(OnScrollDownListener onScrollDown) {
		this.onScrollDown = onScrollDown;
	}

	public interface OnScrollToTopListener {
		public void onScrollTopListener(boolean isTop);
	}

	public interface OnScrollDownListener {
		public void onScrollDown(boolean isDown);
	}

	public void setCategory(String category) {

		firstColumn.removeAllViews();
		secondColumn.removeAllViews();
		for (LoadBitmapTask task : this.taskCollection) {
			task.cancel(true);
		}
		this.infoList.clear();
		this.position = 0;
		galleryItemList.clear();
		PaintingColletctedItems.clear();
		this.page = 1;
		this.category = category;
		picCategory = category;
		this.taskCollection.clear();
		this.setScrollY(0);

		loadMorePaintings(category);

	}

	public GalleryItem mGalleryItem;

	private void addContainner(Bitmap bitmap, int conWidth, int conHeight,
			Map<String, Object> map) {

		int height = conHeight - 60;

		if (mGalleryItem != null) {
			mGalleryItem.setImage(bitmap);
			mGalleryItem.setUploaderText(map.get("up_actor").toString());
			mGalleryItem.setNameText(map.get("name").toString());
			mGalleryItem.setCategory(map.get("category").toString());
			mGalleryItem.setMoods((Integer) map.get("recommendation"),
					(Integer) map.get("count_read"),
					(Integer) map.get("count_comment"));
			mGalleryItem.setUploadTime(map.get("date_on").toString());

			mGalleryItem.getImageView().setTag(R.string.imageview_height,height);
			Picasso.with(getContext()).load(Constant.Api.GET_PHOTO+map.get("up_actor_id"))
			.into(mGalleryItem.getPhotoView());

		} else {
			GalleryItem galleryItem = new MyCollectGalleryItem(getContext());
			galleryItem.getImageView().setTag(R.string.imageview_height, height);
			galleryItem.setImage(bitmap);
			galleryItem.setUploaderText(map.get("up_actor").toString());
			galleryItem.setNameText(map.get("name").toString());
			galleryItem.setCategory(map.get("category").toString());
			galleryItem.setMoods((Integer) map.get("recommendation"),
					(Integer) map.get("count_read"),
					(Integer) map.get("count_comment"));
			
			galleryItem.setPosition(position);
			
			PaintingColletctedItems.add(new PaintingItem(map));
			Picasso.with(getContext()).load(Constant.Api.GET_PHOTO+map.get("up_actor_id"))
			.into(galleryItem.getPhotoView());;
			galleryItem.setUploadTime(map.get("date_on").toString());
			findColumnToAdd(galleryItem, conHeight).addView(
					galleryItem.getContainer());
			galleryItem.setTag(R.string.image_map, map);
			galleryItemList.add(galleryItem);

		}
		
		position++;

	}

	private LinearLayout findColumnToAdd(GalleryItem galleryItem,
			int imageHeight) {
		LogUtils.v("first "+ firstColumnHeight+ " "+secondColumnHeight);
		
		if (firstColumnHeight <= secondColumnHeight) {
			firstColumnHeight += imageHeight;
			return firstColumn;

		} else {
			secondColumnHeight += imageHeight;
			return secondColumn;
		}
		
		
	}

	class LoadBitmapTask extends LoadBitmapAsyTaskUtil{

		public LoadBitmapTask(Context context, int width) {
			super(context, width);
		}

		@Override
		public void postExecute(Bitmap bitmap, Map<String, Object> map) {

			float ratio = bitmap.getWidth() / (columnWidth * 1.0f);
			// by yang 高度加60

			Matrix matrix = new Matrix();
			matrix.postScale(1/ratio,1/ratio); // 长和宽放大缩小的比例
			Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
					bitmap.getWidth(), bitmap.getHeight(), matrix, true);

			int scaledHeight = (int) (bitmap.getHeight() / ratio + 60);

			LogUtils.v("height" + bitmap.getHeight() + " width"
					+ bitmap.getWidth());
			LogUtils.v("scaleHeight" + scaledHeight + " columnWidth"
					+ columnWidth);


			addContainner(resizeBmp, columnWidth, scaledHeight, map);

			taskCollection.remove(this);

		}

	}

}