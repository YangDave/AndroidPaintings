package com.vgpt.androidpaintings.compoent.activity.association;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.compoent.activity.paintings.PictureChangeSizeShowActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;

public class AssociationInfoActivity extends MyActivity{
	TextView asso_nameText;
	TextView founderText;
	TextView presidentText;
	TextView creation_dateText;
	TextView introductionText;
	ImageView markView;
	int asso_id;
	Boolean isMember=false;


	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association_info);
		getActionBar().setTitle(R.string.asso_info);
		loadPage(this);

	}

	private void postGetAssoInfo(JSONObject ret){
		String founder=ret.optString("asso_founder");
		String president=ret.optString("asso_president");
		String creation_date=ret.optString("asso_creation_date");
		String introduction=ret.optString("asso_introduction");
		String asso_name=ret.optString("asso_name");
		asso_nameText.setText(asso_name);
		founderText.setText(founder);
		presidentText.setText(president);
		creation_dateText.setText(creation_date);
		introductionText.setText(introduction);
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.asso_info_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		int itemId = item.getItemId();
		switch(itemId){
		case R.id.asso_info_modify:
			Toast.makeText(AssociationInfoActivity.this, R.string.look_forward, Toast.LENGTH_LONG).show();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void findView() {
		
		asso_nameText=(TextView)findViewById(R.id.asso_name);
		founderText=(TextView)findViewById(R.id.asso_founder);
		presidentText=(TextView)findViewById(R.id.asso_president);
		creation_dateText=(TextView)findViewById(R.id.asso_creation_date);
		introductionText=(TextView)findViewById(R.id.asso_introduction);
		markView=(ImageView)findViewById(R.id.asso_mark);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepareData() {

		Intent intent=getIntent();
		asso_id=(Integer) intent.getSerializableExtra("asso_id");

			Map<String,Object> map=new HashMap<String, Object>();
			map.put("asso_id", asso_id);
			AsycTaskUtil atu = new AsycTaskUtil(AssociationInfoActivity.this, Constant.Association.GET_ASSO_INFO, new OnPostInterface() {

				@Override
				public void code_1(JSONObject json) {

					JSONObject result = json.optJSONObject("result");
					postGetAssoInfo(result);

				}
			});
			atu.execute(map);
	}

	@Override
	public void addListener() {
		
		markView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				if(markView.getResources() != null){
					startActivity(new Intent(AssociationInfoActivity.this,PictureChangeSizeShowActivity.class));
				}
			}
		});
		
		
	}

	@Override
	public void showContent() {

		Picasso.with(AssociationInfoActivity.this).load(Constant.Association.GET_MARK+asso_id)
		.error(R.drawable.empty_photo).into(markView);
	}


}
