package com.vgpt.androidpaintings.test;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.compoent.adapter.AbsListAdapter;

public class TestAdapter extends AbsListAdapter{

	public TestAdapter(Context mContext, List<Map<String, Object>> listItems) {
		super(mContext, listItems);
		// TODO Auto-generated constructor stub
	}
	
	
	class ItemViewHolder{
		TextView text;
		EditText edit;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
	    ItemViewHolder itemViewHolder = null;
	    if( convertView== null){
	    	convertView = LayoutInflater.from(mContext).inflate(R.layout.gallery_item_layout, null);
	    	itemViewHolder = new ItemViewHolder();
	    	convertView.setTag(itemViewHolder);
	    }else{
	    	itemViewHolder = (ItemViewHolder)convertView.getTag();
	    }
	    
		return convertView;
	}

}
