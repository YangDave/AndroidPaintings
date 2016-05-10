package com.vgpt.androidpaintings.compoent.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vgpt.androidpaintings.R;

public class AuctionRecordAdapter extends AbsListAdapter{

	public AuctionRecordAdapter(Context mContext,
			List<Map<String, Object>> listItems) {
		super(mContext, listItems);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ItemViewHolder itemViewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.auction_record_item, null);
			itemViewHolder = new ItemViewHolder();
			convertView.setTag(itemViewHolder);

		} else {
			itemViewHolder = (ItemViewHolder) convertView.getTag();

		}


		itemViewHolder.nameText = (TextView) convertView
				.findViewById(R.id.nameText);
		itemViewHolder.priceText=(TextView)convertView.findViewById(R.id.priceText);
		itemViewHolder.timeText=(TextView)convertView.findViewById(R.id.timeText);
		@SuppressWarnings("unchecked")
		final Map<String, Object> auctionItem = (Map<String, Object>) getItem(position);
		
		itemViewHolder.nameText.setText(auctionItem.get("user_name").toString());
		itemViewHolder.priceText.setText(auctionItem.get("price").toString());
		itemViewHolder.timeText.setText(auctionItem.get("time").toString());
		return convertView;
	}
	
	class ItemViewHolder{
		
		TextView nameText;
		TextView priceText;
		TextView timeText;
	}

}
