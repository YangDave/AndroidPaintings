package com.vgpt.androidpaintings.compoent.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vgpt.androidpaintings.R;

public class AssoNoticeAdapter extends BaseAdapter{
	
	
	public List<Map<String,Object>> mListItems;
	private Context mContext;
	
	public AssoNoticeAdapter(Context mContext, List<Map<String,Object>> mListItems){
		this.mListItems=mListItems;
		this.mContext = mContext;
		
	}
	public List<Map<String,Object>> getListItems(){
		return mListItems;
	};

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListItems.size();
	}

	@Override
	public Map<String,Object> getItem(int position) {
		// TODO Auto-generated method stub
		return mListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemViewHolder itemViewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.notice_item, null);
			itemViewHolder = new ItemViewHolder();
			convertView.setTag(itemViewHolder);

		} else {
			itemViewHolder = (ItemViewHolder) convertView.getTag();

		}
		itemViewHolder.titleText = (TextView) convertView
				.findViewById(R.id.notice_title);
		itemViewHolder.messageText=(TextView)convertView.findViewById(R.id.notice_message);
		itemViewHolder.dateText=(TextView)convertView.findViewById(R.id.notice_date);
		@SuppressWarnings("unchecked")
		Map<String, Object> noticeItem = getItem(position);
		itemViewHolder.titleText.setText(noticeItem.get("message_title").toString());
		itemViewHolder.messageText.setText(noticeItem.get("message").toString());
		itemViewHolder.dateText.setText(noticeItem.get("date").toString());
		
		return convertView;
	}
	class ItemViewHolder {
		TextView titleText, messageText,dateText;
	}
	
}