package com.vgpt.androidpaintings.compoent.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vgpt.androidpaintings.R;

public class CommentListAdapter extends AbsListAdapter{

	public CommentListAdapter(Context mContext,
			List<Map<String, Object>> listItems) {
		super(mContext, listItems);
	}
	
//	@Override
//	public Map<String, Object> getItem(int position) {
//		
//		
//		return super.getItem(getRolatPostion(position));
//	}
//	
//	private int getRolatPostion(int position){
//		
//		
//		if(listItems!=null && !listItems.isEmpty()){
//			
//			
//			return listItems.size()-1-position;
//		}else{
//			
//			
//			return 0;
//		}
//		
//		
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewHolder itemViewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.comment_item, null);
			itemViewHolder = new ItemViewHolder();
			convertView.setTag(itemViewHolder);

		} else {
			itemViewHolder = (ItemViewHolder) convertView.getTag();

		}

	
		itemViewHolder.button=(TextView)convertView.findViewById(R.id.name);
		itemViewHolder.commentText = (TextView) convertView
				.findViewById(R.id.comment);
		itemViewHolder.dateText=(TextView)convertView.findViewById(R.id.date);
		@SuppressWarnings("unchecked")
		final Map<String, Object> commentItem = (Map<String, Object>) getItem(position);
		int user_id=(Integer)commentItem.get("user_id");
		itemViewHolder.button.setText(commentItem.get("username").toString());
		itemViewHolder.button.setTag(user_id);
		itemViewHolder.dateText
				.setText(commentItem.get("date").toString());
		itemViewHolder.commentText.setText(commentItem.get("body")
				.toString()); 
		
//		itemViewHolder.button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent intent=new Intent(mContext,PersonInfoActivity.class);
//				intent.putExtra("person_id", (Integer)commentItem.get("user_id"));
//				mContext.startActivity(intent);
//				
//			}
//		});

		return convertView;
	}
	
	class ItemViewHolder{
		TextView button;
		TextView commentText,dateText;
	}

}
