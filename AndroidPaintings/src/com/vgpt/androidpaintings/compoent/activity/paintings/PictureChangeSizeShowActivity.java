package com.vgpt.androidpaintings.compoent.activity.paintings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.utils.LogUtils;

public class PictureChangeSizeShowActivity extends Activity{
	
	 private static final int NONE = 0;
	    private static final int DRAG = 1;
	    private static final int ZOOM = 2;

	    private int mode = NONE;
	    private float oldDist;
	    private Matrix matrix = new Matrix();
	    private Matrix savedMatrix = new Matrix();
	    private PointF start = new PointF();
	    private PointF mid = new PointF();

	    protected void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_picture_changesize);
	        
	        ImageView view = (ImageView) findViewById(R.id.imageview);
	        
	        Intent intent=getIntent();
	        String url=intent.getStringExtra("url");
//	        Bitmap bm = GetAssetsFile.getImageFromAssetsFile(PictureChangeSizeShowActivity.this, url);
	        Picasso.with(PictureChangeSizeShowActivity.this).load(url).into(view);
//	        view.setImageBitmap(bm);
//	        view.setImageResource(R.drawable.icon);
	        view.setOnTouchListener(mOnTouchListener);
	    }

	    OnTouchListener mOnTouchListener = new OnTouchListener()
	    {
	        public boolean onTouch(View v, MotionEvent event)
	        {
	            LogUtils.v("------------------"+event.toString());
	            ImageView view = (ImageView) v;
	            switch (event.getAction() & MotionEvent.ACTION_MASK)
	            {
	            case MotionEvent.ACTION_DOWN:
	                savedMatrix.set(matrix);
	                start.set(event.getX(), event.getY());
	                mode = DRAG;
	                break;
	            case MotionEvent.ACTION_UP:
	            case MotionEvent.ACTION_POINTER_UP:
	                mode = NONE;
	                break;
	            // 多点触控
	            case MotionEvent.ACTION_POINTER_DOWN:
	                oldDist = spacing(event);
	                if (oldDist > 10f)
	                {
	                    savedMatrix.set(matrix);
	                    midPoint(mid, event);
	                    mode = ZOOM;
	                }
	                break;
	            case MotionEvent.ACTION_MOVE:
	                if (mode == DRAG)
	                {
	                    matrix.set(savedMatrix);
	                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
	                }
	                else if (mode == ZOOM)
	                {
	                    float newDist = spacing(event);
	                    if (newDist > 10f)
	                    {
	                        matrix.set(savedMatrix);
	                        float scale = newDist / oldDist;
	                        matrix.postScale(scale, scale, mid.x, mid.y);
	                        LogUtils.v("------------------"+newDist);
	                    }
	                }
	                break;
	            }

	            view.setImageMatrix(matrix);
//	            view.invalidate();
	            return true;
	        }

	    };

	    @SuppressLint("FloatMath")
		private float spacing(MotionEvent event)
	    {
	        float x = event.getX(0) - event.getX(1);
	        float y = event.getY(0) - event.getY(1);
	        return FloatMath.sqrt(x * x + y * y);
	    }

	    private void midPoint(PointF point, MotionEvent event)
	    {
	        float x = event.getX(0) + event.getX(1);
	        float y = event.getY(0) + event.getY(1);
	        point.set(x / 2, y / 2);
	    }

	    public void onClickBack(View view)
	    {
	        this.finish();
	    }    

}
