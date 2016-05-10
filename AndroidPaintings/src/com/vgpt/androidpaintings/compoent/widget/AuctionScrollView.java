package com.vgpt.androidpaintings.compoent.widget;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
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

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.biz.BitmapChangeSize;
import com.vgpt.androidpaintings.biz.ImageLoader;
import com.vgpt.androidpaintings.biz.JSONToMap;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.AuctionItem;
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
public class AuctionScrollView extends ScrollView implements OnTouchListener {

	private OnScrollToTopListener onScrollToTop;

	private OnScrollDownListener onScrollDown;

	private static final String GALLERYJSON = "galleryJson";

	/**
	 * 每页要加载的图片数量
	 */
	public static final int PAGE_SIZE = 4;
	public static int user_id = MainActivity.user_id;
	public static final int IMAGE = 100001;
	public static final int NAMEVIEW = 100002;
	public static final int UPLOADERVIEW = 100003;
	public static final int HEADVIEW = 100004;

	public String category = "all";
	
	public static String auctionCategory = "all";
	public static List<AuctionItem> auctionItems = new ArrayList<AuctionItem>();
	/*
	 * 记录当前已加载到第几页
	 */
	private int page = 1;
	
	private int position = 0;

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
	private static Set<LoadBitmapTask> taskCollection;

	/*
	 * 图画的文本信息存储集合
	 */
	private List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();

	/**
	 * auctionScrollView下的直接子布局。
	 */
	private static View scrollLayout;

	/**
	 * auctionScrollView布局的高度。
	 */
	private static int scrollViewHeight;

	/**
	 * 记录上垂直方向的滚动距离。
	 */
	private static int lastScrollY = -1;

	/**
	 * 记录所有界面上的图片，用以可以随时控制对图片的释放。
	 */
	
	private boolean isWorking=false;

	public List<AuctionGalleryItem> auctionGalleryItemList = new ArrayList<AuctionGalleryItem>();


	/**
	 * 在Handler中进行图片可见性检查的判断，以及加载更多图片的操作。
	 */

	Map<String, Object> map = new HashMap<String, Object>();

	Bitmap emptybm = BitmapFactory.decodeResource(getResources(), R.drawable.empty_photo);
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			AuctionScrollView auctionScrollView = (AuctionScrollView) msg.obj;
			int scrollY = auctionScrollView.getScrollY();
			// 如果当前的滚动位置和上次相同，表示已停止滚动
			if (scrollY == lastScrollY) {
				// 当滚动的最底部，并且当前没有正在下载的任务时，开始加载下一页的图片
				if (scrollViewHeight + scrollY + 200 >= scrollLayout
						.getHeight() && taskCollection.isEmpty()) {
					auctionScrollView.loadMorePaintings(category);
					LogUtils.v("============= next is done==========");

				}
				auctionScrollView.checkVisibility();
			} else {
				lastScrollY = scrollY;
				Message message = new Message();
				message.obj = auctionScrollView;
				LogUtils.v("============= handler is done==========");
				// 5毫秒后再次对滚动位置进行判断
				handler.sendMessageDelayed(message, 50);
			}

		};

	};

	/**
	 * auctionScrollView的构造函数。
	 * 
	 * @param context
	 * @param attrs
	 */
	public AuctionScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		imageLoader = ImageLoader.getInstance();
		taskCollection = new HashSet<LoadBitmapTask>();
		setOnTouchListener(this);
	}

	/**
	 * 进行一些关键性的初始化操作，获取auctionScrollView的高度，以及得到第一列的宽度值。并在这里开始加载第一页的图片。
	 */
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			scrollViewHeight = getHeight();
			scrollLayout = getChildAt(0);
			firstColumn = (LinearLayout) findViewById(R.id.first_column);
			secondColumn = (LinearLayout) findViewById(R.id.second_column);
			columnWidth = firstColumn.getWidth();
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
			map.put("user_id", user_id);
			map.put("size", PAGE_SIZE);
			map.put("page", page);
			map.put("category", category);
			//			GetListTask task = new GetListTask();
			//			task.execute(map);
			AsycTaskWithWorkingUtil atu = new AsycTaskWithWorkingUtil(getContext(), Constant.Auction.GET_AUCTION_LIST, new OnPostWithWorkingInterface() {

				@Override
				public void code_1(JSONObject json) {

					JSONArray retItems = json.optJSONArray("result");
					if (retItems != null) {
						page++;
						for (int i = 0; i < retItems.length(); i++) {
							JSONObject item = retItems.optJSONObject(i);

							Map<String, Object> map;
							try {
								map = JSONToMap.toMap(item);
								LoadBitmapTask lit = new LoadBitmapTask(getContext(),columnWidth);
								lit.execute(map);
								infoList.add(map);
							} catch (JSONException e) {
								LogUtils.e(e);
								e.printStackTrace();
							}

						}
					}

				}

				@Override
				public void finishPost() {
					isWorking =false;
					
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
		for (int i = 0; i < auctionGalleryItemList.size(); i++) {
			AuctionGalleryItem auctionGalleryItem = auctionGalleryItemList.get(i);
			int borderTop = auctionGalleryItem.getTop();
			int borderBottom = auctionGalleryItem.getBottom();
			if (borderBottom + 300 > getScrollY()
					&& borderTop - 300 < getScrollY() + scrollViewHeight) {
				Map<String, Object> map = (Map<String, Object>) auctionGalleryItem
						.getTag(R.string.image_map);
				Bitmap bitmap =
						imageLoader.getBitmapFromMemoryCache(Constant.Painting.GET_PICTURE+map.get("pic_id").toString()+".jpeg");
				if (bitmap != null) {
					int height = (Integer)auctionGalleryItem.getImageView().getTag(R.string.imageview_height);
					float heightF = (float)height;
					Bitmap resizeBm = BitmapChangeSize.big(bitmap, (float)columnWidth, heightF);
					auctionGalleryItem.setImage(resizeBm);
				} else {
					LoadBitmapTask task = new LoadBitmapTask(getContext(),columnWidth);
					task.execute(map);
				}
			} else {
				int height = (Integer)auctionGalleryItem.getImageView().getTag(R.string.imageview_height);

				float heightF = (float)height;

				Bitmap resizeBm = BitmapChangeSize.big(emptybm, (float)columnWidth, heightF);

				auctionGalleryItem.getImageView().setImageBitmap(resizeBm);
			}
		}
	}

	private LinearLayout findColumnToAdd(AuctionGalleryItem auctionGalleryItem,
			int imageHeight) {
		if (firstColumnHeight <= secondColumnHeight) {
			firstColumnHeight += imageHeight;
			return firstColumn;

		} else {
			secondColumnHeight += imageHeight;
			return secondColumn;

		}
	}


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

	// public interface OnItemClickListener{
	// public void onClick(int position);
	// }
	public void setCategory(String category) {

		firstColumn.removeAllViews();
		secondColumn.removeAllViews();
		for (LoadBitmapTask task : this.taskCollection) {
			task.cancel(true);
		}
		this.infoList.clear();
		auctionItems.clear();
		position = 0;
		this.auctionGalleryItemList.clear();
		this.page = 1;
		this.category = category;
		auctionCategory = category;
		this.taskCollection.clear();
		this.setScrollY(0);

		loadMorePaintings(category);

	}

	public Bitmap getImageFromAssetsFile(String fileName) {
		Bitmap image = null;
		InputStream is = null;
		AssetManager am = getResources().getAssets();
		try {
			is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (is != null) {

				IOUtils.closeQuietly(is);
			}

		}
		return image;

	}


	private AuctionGalleryItem mAuctionGalleryItem;
	private void addContainner(Bitmap bitmap, int conWidth, int conHeight,
			Map<String, Object> map) {

		int height = conHeight - 60;

		if (mAuctionGalleryItem != null) {
			
			mAuctionGalleryItem.setImage(bitmap);
			mAuctionGalleryItem.setUploaderText((String)map.get("up_actor_name") );
			mAuctionGalleryItem.setCategory((String)map.get("category") );
			mAuctionGalleryItem.setNameText((String)map.get("pic_name"));
			mAuctionGalleryItem.setRemainTime((Integer)map.get("remain_time"));
			mAuctionGalleryItem.setPriceTimes((Integer)map.get("price_times") );
			mAuctionGalleryItem.setCurrentPrice((Integer)map.get("current_price") );
			mAuctionGalleryItem.setTag(R.string.image_map,map);
			mAuctionGalleryItem.getImageView().setTag(R.string.imageview_height,height);


		} else {

			AuctionGalleryItem auctionGalleryItem = new AuctionGalleryItem(getContext());
			auctionGalleryItem.setImage(bitmap);
			auctionGalleryItem.setUploaderText((String)map.get("up_actor_name") );
			auctionGalleryItem.setCategory((String)map.get("category") );
			auctionGalleryItem.setNameText((String)map.get("pic_name"));
			auctionGalleryItem.setRemainTime((Integer)map.get("remain_time"));
			auctionGalleryItem.setPriceTimes((Integer)map.get("price_times") );
			auctionGalleryItem.setCurrentPrice((Integer)map.get("current_price") );
			auctionGalleryItem.setTag(R.string.image_map,map);
			auctionGalleryItem.getImageView().setTag(R.string.imageview_height, height);
			auctionItems.add(new AuctionItem().setAttribute(map));
			auctionGalleryItem.setPosition(position);

			findColumnToAdd(auctionGalleryItem, conHeight).addView(
					auctionGalleryItem.getContainer());
			auctionGalleryItemList.add(auctionGalleryItem);

		}
		position++;

	}
	

	class LoadBitmapTask extends LoadBitmapAsyTaskUtil{

		public LoadBitmapTask(Context context, int width) {
			super(context, width);
			// TODO Auto-generated constructor stub
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