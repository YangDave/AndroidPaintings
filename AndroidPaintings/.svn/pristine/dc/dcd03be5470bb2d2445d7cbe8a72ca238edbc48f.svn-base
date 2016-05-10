package com.vgpt.androidpaintings.compoent.activity.association;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.compoent.adapter.AssoNoticeAdapter;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.Association;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.AsycTaskWithProgressUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class AssociationNoticeActivity extends MyActivity{
	Association association=null;
	TextView asso_nameText;
	int asso_id;
	ListView noticeList;
	AssoNoticeAdapter adapter;
	JSONArray noticeArray;

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association_notice);
		getActionBar().setTitle(R.string.asso_notice);

		loadPage(this);
		refreshNoticeList();

	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.asso_notice_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if(item.getItemId() == R.id.edit_notice){
			creatAndShowDialog();
		}

		return super.onMenuItemSelected(featureId, item);
	}

	private void creatAndShowDialog(){
		AlertDialog.Builder builder = new Builder(AssociationNoticeActivity.this);
		final View view = getLayoutInflater().inflate(R.layout.notice_create_dialog, null);
		builder.setTitle(R.string.notice_create_dialog)
		.setIcon(R.drawable.association1)
		.setView(view)
		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

				String title = ((EditText)view.findViewById(R.id.edit_title)).getText().toString().trim();
				String content = ((EditText)view.findViewById(R.id.edit_content)).getText().toString().trim();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("title", title);
				map.put("body", content);
				map.put("user_id", user_id);
				map.put("asso_id", asso_id);
				handleNotice(map);

			}
		})
		.setNegativeButton(R.string.cancel, null);
		builder.show();



	}

	@SuppressWarnings("unchecked")
	private void handleNotice(Map<String,Object> map){


		AsycTaskWithProgressUtil atu = new AsycTaskWithProgressUtil(AssociationNoticeActivity.this,
				Constant.Association.SET_ASSO_NOTICE, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONObject result = json.optJSONObject("result");
				if(result.optBoolean("success")){
					Toast.makeText(AssociationNoticeActivity.this, "通知创建成功", Toast.LENGTH_LONG).show();
					refreshNoticeList();
				}else{
					Toast.makeText(AssociationNoticeActivity.this, "通知创建失败", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		atu.execute(map);

	}


	@Override
	public void findView() {

		noticeList=(ListView)findViewById(R.id.noticelist);
	}




	@SuppressWarnings("unchecked")
	@Override
	public void prepareData() {
		Intent intent=getIntent();
		asso_id=intent.getIntExtra("asso_id", 0);
		LogUtils.v("asso_id = "+asso_id);
		getWindow().setTitle(intent.getStringExtra("asso_name")+getString(R.string.asso_notice));

		adapter=new AssoNoticeAdapter(this,new ArrayList<Map<String,Object>>());
		noticeList.setAdapter(adapter);

	}




	@Override
	public void addListener() {
		// TODO Auto-generated method stub

	}




	@Override
	public void showContent() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	private void refreshNoticeList(){
		
		adapter.getListItems().clear();

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("asso_id", asso_id);
		AsycTaskUtil atu = new AsycTaskUtil(AssociationNoticeActivity.this, 
				Constant.Association.GET_ASSO_NOTICE, new OnPostInterface() {
			@Override
			public void code_1(JSONObject json) {

				JSONArray noticeArray = json.optJSONArray("result");
				for(int i=0;i<noticeArray.length();i++){
					Map<String,Object> listItem=new HashMap<String,Object>();
					JSONObject noticeObject=noticeArray.optJSONObject(i);
					listItem.put("message_title", noticeObject.optString("message_title"));
					listItem.put("message", noticeObject.optString("message"));
					listItem.put("date", noticeObject.optString("date"));
					adapter.getListItems().add(listItem);
				}
				adapter.notifyDataSetChanged();

			}
		});

		atu.execute(map);
	}

}
