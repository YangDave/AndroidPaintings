package com.vgpt.androidpaintings.compoent.activity.association;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.biz.JSONArrayToList;
import com.vgpt.androidpaintings.compoent.adapter.AssoExerAppliedListAdapter;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class AssociationManeuverDetailActivity extends MyActivity {

	private int asso_id;
	private int exer_id;

	TextView mTitleView;
	TextView mBodyView;
	TextView mIssuerView;
	TextView mBeginView;
	TextView mEndView;
	TextView mPlaceView;
	TextView mDateCreateView;
	Button mJoinInButton;
	ListView mListView;
	AssoExerAppliedListAdapter adapter = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association_maneuver_detail);
		loadPage(this);
		

		refreshNameList();

	}

	@Override
	public void findView() {

		mTitleView = (TextView)findViewById(R.id.maneuver_title);
		mBodyView = (TextView)findViewById(R.id.maneuver_content);
		mIssuerView = (TextView)findViewById(R.id.maneuver_issuer);
		mBeginView = (TextView)findViewById(R.id.maneuver_start_date);
		mEndView = (TextView)findViewById(R.id.maneuver_over_date);
		mPlaceView = (TextView)findViewById(R.id.maneuver_place);
		mDateCreateView = (TextView)findViewById(R.id.maneuver_issue_date);
		mJoinInButton = (Button)findViewById(R.id.join_in);
		mListView = (ListView)findViewById(R.id.maneuver_member_list);

	}

	@Override
	public void prepareData() {

		Intent intent = getIntent();
		asso_id = intent.getIntExtra("asso_id", 0);
		exer_id = intent.getIntExtra("exer_id", 0);
		LogUtils.v("==========exer_id========="+exer_id);
		adapter = new AssoExerAppliedListAdapter(this, new ArrayList<Map<String,Object>>());
		mListView.setAdapter(adapter);

	}
	
	

	@Override
	public void addListener() {

		mJoinInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				toJoinIn();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void showContent() {

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("exer_id", exer_id);
		map.put("user_id",user_id);

		AsycTaskUtil atu = new AsycTaskUtil(AssociationManeuverDetailActivity.this,
				Constant.Association.GET_EXERCISE_INFO, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {


				onPostExecute(json.optJSONObject("result"));
			}
		});

		atu.execute(map);



	}

	private void onPostExecute(JSONObject json){
		mTitleView.setText(json.optString("title"));
		mBodyView.setText(json.optString("body"));
		mBeginView.setText(json.optString("begin_exercise"));
		mEndView.setText(json.optString("end_exercise"));
		mPlaceView.setText(json.optString("place"));
		mDateCreateView.setText(json.optString("date_creation"));
		mIssuerView.setText(json.optString("publisher"));
		mIssuerView.setTag(json.optInt("publisher_id"));
	}

	@SuppressWarnings("unchecked")
	private void toJoinIn(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("exer_id", exer_id);
		AsycTaskUtil atu = new AsycTaskUtil(AssociationManeuverDetailActivity.this,
				Constant.Association.EXER_APPLY, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONObject result = json.optJSONObject("result");
				if(result.optBoolean("success")){
					Toast.makeText(AssociationManeuverDetailActivity.this, "报名成功", Toast.LENGTH_LONG).show();
					refreshNameList();
				}else{
					Toast.makeText(AssociationManeuverDetailActivity.this, "报名失败", Toast.LENGTH_LONG).show();
				}

			}
		});

		atu.execute(map);
	}

	@SuppressWarnings("unchecked")
	private void refreshNameList(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("exer_id", exer_id);
		map.put("page", 1);
		map.put("size", 50);

		AsycTaskUtil atu = new AsycTaskUtil(AssociationManeuverDetailActivity.this,
				Constant.Association.EXER_APPLIED_LIST, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONArray jsonList = json.optJSONArray("result");
				List<Map<String, Object>> list = null;
				try {
					list = JSONArrayToList.toList(jsonList);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				adapter.getlistItems().removeAll(adapter.getlistItems());
				adapter.getlistItems().addAll(list);

				adapter.notifyDataSetChanged();
			}
		});

		atu.execute(map);


	}
}
