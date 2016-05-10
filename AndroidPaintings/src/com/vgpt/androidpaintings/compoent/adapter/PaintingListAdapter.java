package com.vgpt.androidpaintings.compoent.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.utils.LogUtils;

public class PaintingListAdapter extends AbsListAdapter{

	public PaintingListAdapter(Context mContext,
			List<Map<String, Object>> listItems) {
		super(mContext, listItems);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewHolder itemViewHolder=null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.simple_item, null);
			itemViewHolder = new ItemViewHolder();
			convertView.setTag(itemViewHolder);

		} else {
			itemViewHolder = (ItemViewHolder) convertView.getTag();

		}

		itemViewHolder.painting = (ImageView) convertView
				.findViewById(R.id.painting);
		itemViewHolder.nameText = (TextView) convertView
				.findViewById(R.id.name);
//		itemViewHolder.introductionText = (TextView) convertView
//				.findViewById(R.id.introduction);
		@SuppressWarnings("unchecked")
		Map<String, Object> picItem = (Map<String, Object>) getItem(position);
		int pic_id=(Integer)picItem.get("pic_id");
		LogUtils.v("======"+"my upload_pic "+picItem.get("name").toString());
		itemViewHolder.nameText
				.setText(picItem.get("name").toString());
//		itemViewHolder.introductionText.setText(picItem.get("introduction").toString()); 
		Picasso.with(mContext).load(Constant.Painting.GET_PICTURE+pic_id).into(itemViewHolder.painting);

		return convertView;
	}
	
	class ItemViewHolder{
		ImageView painting;
		TextView nameText,introductionText;
	}
}

	