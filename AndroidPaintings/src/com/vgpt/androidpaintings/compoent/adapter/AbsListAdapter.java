package com.vgpt.androidpaintings.compoent.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbsListAdapter extends BaseAdapter{
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	
	
	

	public Context mContext;
	public List<Map<String, Object>> listItems;

	public List<Map<String, Object>> getlistItems() {
		return listItems;
	}

	public void setlistItems(List<Map<String, Object>> listItems) {
		this.listItems = listItems;
	}

	@SuppressWarnings("unchecked")
	public AbsListAdapter(Context mContext,
			List<Map<String, Object>> listItems) {
		this.listItems = listItems;
		this.mContext = mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public int getCount() {

		return listItems.size();
	}

	@Override
	public Map<String,Object> getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	public abstract View getView(int position, View convertView, ViewGroup parent);
	

}