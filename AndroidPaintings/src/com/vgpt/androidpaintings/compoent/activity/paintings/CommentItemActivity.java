package com.vgpt.androidpaintings.compoent.activity.paintings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.CommentItem;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;
import com.vgpt.androidpaintings.utils.LogUtils;

public class CommentItemActivity extends Activity{
	
	Button nameBt,reEidtBt;
	TextView dateView,contextView;
	EditText commEdit;
	int user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_comment_item);
		
		nameBt=(Button)findViewById(R.id.username);
		dateView=(TextView)findViewById(R.id.comment_date);
		contextView=(TextView)findViewById(R.id.comment_context);
		reEidtBt=(Button)findViewById(R.id.reEdit);
		
		Intent intent=getIntent();
		
		CommentItem commentItem=(CommentItem)intent.getSerializableExtra("commentItem");
		user_id=intent.getIntExtra("user_id", 0);
		
		nameBt.setTag(commentItem.getUser_id());
		nameBt.setText(commentItem.getUsername());
		LogUtils.v(commentItem.getUsername());
		dateView.setText(commentItem.getDate());
		contextView.setText(commentItem.getContext());
		contextView.setTag(commentItem.getComment_id());
		
		if(user_id==(Integer)nameBt.getTag()){
			reEidtBt.setVisibility(View.VISIBLE);
		}
		
		reEidtBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				AlertDialog.Builder builder=new Builder(CommentItemActivity.this);
				View view=getLayoutInflater().inflate(R.layout.comment_edit, null);
				commEdit=(EditText)view.findViewById(R.id.commenteditText);
				commEdit.setText(contextView.getText());
				commEdit.setSelection(contextView.getText().toString().length());
				builder.setView(view);
				builder.setNegativeButton("取消", null);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@SuppressWarnings("unchecked")
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("comment_id", (Integer)contextView.getTag());
						map.put("body", commEdit.getText().toString());
						
						AlterCommTask alterTask=new AlterCommTask();
						alterTask.execute(map);
						
					}
				});
				
				builder.show();
				
			}
		});
		
		
		
		
		
	}
	
	class AlterCommTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		private int checkCode;
		private JSONObject ret;

		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {
			MyHttpClient cl=MyHttpClient.getInstance();
			List<NameValuePair> list=new ArrayList<NameValuePair>();
			JSONObject json=new JSONObject(params[0]);
			NameValuePair pa=new BasicNameValuePair("json", json.toString());
			list.add(pa);
			
			
				try {
					cl.post(Constant.Painting.ALTER_COMMENT, list, 
							new JSONResponseHandler<JSONObject>() {

								@Override
								public JSONObject handlerJson(JSONObject json) {
									
									
									if(json!=null){
										checkCode=json.optInt("code");
										if(checkCode==1){
											ret=json.optJSONObject("result");
										}
									}
									return json;
								}
					});
				} catch (Exception e) {
					e.printStackTrace();
					
				}
		
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			
			if(checkCode==1){
				if(ret!=null){
				     boolean isSuccess=ret.optBoolean("success");
				     if(isSuccess){
				    	 Toast.makeText(CommentItemActivity.this, "修改成功", Toast.LENGTH_LONG).show();
				    	 contextView.setText(commEdit.getText().toString());
				     }else{
				    	 Toast.makeText(CommentItemActivity.this, "修改失败", Toast.LENGTH_LONG).show();
				     }
				}
			}else{
				
				Toast.makeText(CommentItemActivity.this, "未知错误", Toast.LENGTH_LONG).show();
			}
		}
		
		
	}
	
	

}
