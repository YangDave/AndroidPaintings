package com.vgpt.androidpaintings.compoent.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AssoExerAppliedListAdapter extends AbsListAdapter {

	public AssoExerAppliedListAdapter(Context mContext,
			List<Map<String, Object>> listItems) {
		super(mContext, listItems);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ItemViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
			holder = new ItemViewHolder();
			convertView.setTag(holder);
		}else{
			holder = (ItemViewHolder)convertView.getTag();
		}

		holder.nameText = (TextView)convertView.findViewById(android.R.id.text1);
		holder.nameText.setText(getItem(position).get("user_name").toString());

		return convertView;
	}

	class ItemViewHolder{
		TextView nameText;
	}

}
