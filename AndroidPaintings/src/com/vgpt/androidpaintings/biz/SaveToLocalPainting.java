package com.vgpt.androidpaintings.biz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.os.Environment;

import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.utils.LogUtils;

public class SaveToLocalPainting {
	
	public static void saveHeadPhoto(int user_id,Bitmap bm){
		try {
			String fileDir=Constant.FileDir.HEADPHOTO;

			File f = new File(fileDir);
			if (!f.exists()) {
				f.mkdir();
			}
			File file = new File(fileDir+user_id+".jpeg");
			if(file.exists()&& file.isFile()){
				file.delete();
				LogUtils.v("file is exist");
			}
			if(!file.exists()){
				LogUtils.v("===========file is deleted");
				file = new File(fileDir+String.valueOf(user_id)+".jpeg");
			}
				
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e(e);
		}
	}
	
	public static void savePicture(int pic_id,Bitmap bm){
		try {
			String fileDir=Constant.FileDir.PIC;

			File f = new File(fileDir);
			if (!f.exists()) {
				f.mkdir();
			}
			File file = new File(fileDir+String.valueOf(pic_id)+".jpeg");
			if(file.exists()&& file.isFile()){
				file.delete();
				LogUtils.v("file is exist");
			}
			if(!file.exists()){
				LogUtils.v("===========file is deleted");
				file = new File(fileDir+String.valueOf(pic_id)+".jpeg");
			}
				
			
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			LogUtils.e(e);
		}
	}
	
	public static void saveMark(int asso_id,Bitmap bm){
		try {
			String fileDir=Constant.FileDir.ASSOMARK;

			File f = new File(fileDir);
			if (!f.exists()) {
				f.mkdir();
			}
			File file = new File(fileDir+String.valueOf(asso_id)+".jpeg");
			if(file.exists()&& file.isFile()){
				file.delete();
				LogUtils.v("file is exist");
			}
			if(!file.exists()){
				LogUtils.v("===========file is deleted");
				file = new File(fileDir+String.valueOf(asso_id)+".jpeg");
			}
				
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			LogUtils.e(e);
		}
	}
	
	public static void savePaintingToUpload(Bitmap bm,String paintingName){
		try {
			String dir = Environment.getExternalStorageDirectory().getPath();

			LogUtils.v("======================================" + dir);
			File f = new File(Constant.MainDir);

			if (!f.exists()) {
				f.mkdir();
			}
			File file = new File(Constant.MainDir+paintingName);
			if(file.exists()&& file.isFile()){
				file.delete();
				LogUtils.v("file is exist");
			}

			OutputStream outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			LogUtils.e(e);
		}
	}

}
