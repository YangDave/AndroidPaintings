package com.vgpt.androidpaintings.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import android.os.Environment;
import android.util.Log;

import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class DownloadFileUtil {
	
	public  File downloadImage(String imageUrl) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			Log.d("TAG", "monted sdcard");
		} else {
			Log.d("TAG", "has no sdcard");
		}
		HttpURLConnection con = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		File imageFile = null;
		InputStream in = null;
		try {
			int lastSlashIndex = imageUrl.lastIndexOf(".");
			String imageName = imageUrl.substring(0, lastSlashIndex);

			int lsi = imageName.lastIndexOf("=");

			String pic_idStr = imageName.substring(lsi + 1);

			in = MyHttpClient.getInstance().get(imageName);

			bis = new BufferedInputStream(in);
			imageFile = new File(getImagePath(pic_idStr));
			fos = new FileOutputStream(imageFile);
			bos = new BufferedOutputStream(fos);
			byte[] b = new byte[1024];
			int length;
			while ((length = bis.read(b)) != -1) {
				bos.write(b, 0, length);
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (con != null) {
					con.disconnect();
				}
				if (in != null) {

					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imageFile;

		
		
	}
	
	private String getImagePath(String imageUrl) {
		String imageDir = Constant.FileDir.PIC;
		File file = new File(imageDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		String imagePath = imageDir + imageUrl + ".jpeg";
		return imagePath;
	}

}
