package com.vgpt.androidpaintings.compoent.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vgpt.androidpaintings.R;

public class AssoManeuverListAdapter extends AbsListAdapter{

	public AssoManeuverListAdapter(Context mContext,
			List<Map<String, Object>> listItems) {
		super(mContext, listItems);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.maneuver_item, null);
			holder = new ItemViewHolder();
			convertView.setTag(holder);
		}else{
			holder = (ItemViewHolder)convertView.getTag();
		}
		
		holder.titleText = (TextView)convertView.findViewById(R.id.maneuver_title);
		holder.contentText = (TextView)convertView.findViewById(R.id.maneuver_content);
		
		Map<String,Object> map = getItem(position);
//		TODO load image
		holder.titleText.setText(map.get("title").toString());
		holder.contentText.setText(map.get("body").toString());
		
		return convertView;
	}
	
	
	class ItemViewHolder{
		
		TextView titleText,contentText;
	}

}
