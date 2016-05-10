package com.vgpt.androidpaintings.compoent.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyListAdapter<T> extends BaseAdapter{
	
	private List<T> list = new ArrayList<T>();
	private Context context;
	private int layoutId = 0;
	
	public MyListAdapter(Context context,int layoutId){
		this.context = context;
		this.layoutId = layoutId;
		
	}
	
	public void addItem(T t){
		list.add(t);
	}
	
	public void remove(int position){
		list.remove(position);
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(layoutId, null);
		}
		
		initItemView(position, convertView,parent);
		return convertView;
	}
	
	public abstract void initItemView(int position,View convertView,ViewGroup parent);

}
