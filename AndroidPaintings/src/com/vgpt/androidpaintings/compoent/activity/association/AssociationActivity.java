package com.vgpt.androidpaintings.compoent.activity.association;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.compoent.activity.paintings.PictureChangeSizeShowActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.Association;

public class AssociationActivity extends MyActivity{
	TextView associationNameText;

	Association association=null;
	ImageView mark;
	String asso_name;
	String url;
	int asso_id=0;
	ImageView assoInfo,assoMember,assoNotice,assoActivity,assoMore,assoWorks;
	Drawable drawable;


	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association_single);

		loadPage(this);
		


	}


	@Override
	public void findView() {

		associationNameText=(TextView)findViewById(R.id.associationname);
		mark=(ImageView)findViewById(R.id.mark);
		assoInfo=(ImageView)findViewById(R.id.association_info);
		assoMember=(ImageView)findViewById(R.id.association_member);
		assoNotice=(ImageView)findViewById(R.id.association_notice);
		assoActivity=(ImageView)findViewById(R.id.association_activity);
		assoMore=(ImageView)findViewById(R.id.association_more);
		assoWorks = (ImageView)findViewById(R.id.association_works);

	}


	@Override
	public void prepareData() {
		Intent intent=getIntent();

		association=(Association)intent.getSerializableExtra("association");
		asso_name=association.getAsso_name();
		url=association.getMark_url();
		asso_id=association.getAsso_id();
		
		getActionBar().setTitle(asso_name);
	}


	@Override
	public void addListener() {

		assoInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(asso_id!=0){

					Intent assoInfoIntent=new Intent(AssociationActivity.this,AssociationInfoActivity.class);
					assoInfoIntent.putExtra("asso_id", asso_id);
					assoInfoIntent.putExtra("asso_name", asso_name);
					assoInfoIntent.putExtra("ismember", true);
					startActivity(assoInfoIntent);
				}}
		}); 
		assoActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent activityIntent = new Intent(AssociationActivity.this,AssociationManeuverActivity.class);
				activityIntent.putExtra("asso_id", asso_id);
				activityIntent.putExtra("asso_name",asso_name);
				startActivity(activityIntent);
			}
		});
		assoMember.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(asso_id!=0){

					Intent assoMemberIntent=new Intent(AssociationActivity.this,AssociationMemberActivity.class);
					assoMemberIntent.putExtra("asso_id", asso_id);
					assoMemberIntent.putExtra("asso_name", asso_name);
					startActivity(assoMemberIntent);

				}
			}
		});
		assoNotice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(asso_id!=0){
					Intent assoNoticeIntent=new Intent(AssociationActivity.this,AssociationNoticeActivity.class);
					assoNoticeIntent.putExtra("asso_id", asso_id);
					assoNoticeIntent.putExtra("asso_name", asso_name);
					startActivity(assoNoticeIntent);

				}

			}
		});

		mark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AssociationActivity.this,PictureChangeSizeShowActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);

			}
		});

	}


	@Override
	public void showContent() {

		associationNameText.setText(asso_name);
		url=Constant.Association.GET_MARK+asso_id;
		Picasso.with(AssociationActivity.this).load(url).error(R.drawable.empty_photo).into(mark);

	}
}
