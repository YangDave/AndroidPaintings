package com.vgpt.androidpaintings.biz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * @author Charles
 * can't work
 */
public class ImageFileCache {
	private static final String CACHDIR="ImgCach";
	private static final String WHOLESALE_CONV=".cach";
	private static final int MB = 1024*1024; private static final int CACHE_SIZE = 50;
	 private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 50; 
	public ImageFileCache(){
		removeCache(getDirectory());
	}
	public Bitmap getImage(final String url){
		final String path=getDirectory()+"/"+convertUrlToFileName(url);
		File file=new File(path);
		if(file.exists()){
			Bitmap bmp=BitmapFactory.decodeFile(path);
			if(bmp==null){
				file.delete();
			}
			else{ updateFileTime(path);
			return bmp;}
		}
		return null;
	}
	public void savedBitmap(Bitmap bm, String url){
		if(bm==null){
			return;
		}
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) { 
			return; 
			} 
		String filename=convertUrlToFileName(url);
		String dir=getDirectory();
		File dirFile = new File(dir);
		 if (!dirFile.exists())
		 dirFile.mkdirs();
		 //dirFile.delete();
		 File file = new File(dir +"/" + filename); 
		try { 
		file.createNewFile();
		 OutputStream outStream = new FileOutputStream(file); 
		bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream); 
		outStream.flush();
		outStream.close(); 
		} catch (FileNotFoundException e) { 
		Log.w("ImageFileCache", "FileNotFoundException"); 
		} catch (IOException e) {
		 Log.w("ImageFileCache", "IOException"); 
		} 
		} 
		/** * ����洢Ŀ¼�µ��ļ���С�� * ���ļ��ܴ�С���ڹ涨��CACHE_SIZE����sdcardʣ��ռ�С��FREE_SD_SPACE_NEEDED_TO_CACHE�Ĺ涨 * ��ôɾ��40%���û�б�ʹ�õ��ļ� */
		 private boolean removeCache(String dirPath) {
		 File dir = new File(dirPath);
		 File[] files = dir.listFiles(); 
		if (files == null) {
		 return true; 
		} 
		if (!android.os.Environment.getExternalStorageState().equals( android.os.Environment.MEDIA_MOUNTED)) 
		{
		 return false; 
		}
		 int dirSize = 0;
		 for (int i = 0; i < files.length; i++) {
		 if (files[i].getName().contains(WHOLESALE_CONV)) { 
		dirSize += files[i].length(); 
		} 
		}
		 if (dirSize > CACHE_SIZE * MB || FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
		 int removeFactor = (int) ((0.4 * files.length) + 1);
		 Arrays.sort(files, new FileLastModifSort());
		 for (int i = 0; i < removeFactor; i++) { 
		if (files[i].getName().contains(WHOLESALE_CONV)) {
		 System.out.println("deleted..."+files[i].getName()); 
		  
		}
		 }
		 } 
		if (freeSpaceOnSd() <= CACHE_SIZE) { 
		return false; 
		} 
		Log.i("ImageFileCache", "file.size = "+files.length); 
		return true; 
		} 
		/** �޸��ļ�������޸�ʱ�� **/
		 public void updateFileTime(String path) { 
		File file = new File(path); 
		long newModifiedTime = System.currentTimeMillis(); 
		file.setLastModified(newModifiedTime);
		} 
		/** ����sdcard�ϵ�ʣ��ռ� **/
		 private int freeSpaceOnSd() {
		 StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath()); 
		double sdFreeMB = ((double)stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
		 return (int) sdFreeMB; 
		}
		 /** ��urlת���ļ��� **/
		 private String convertUrlToFileName(String url) { 
		String[] strs = url.split("/");
		 return strs[strs.length - 1] + WHOLESALE_CONV; } 
		/** ��û���Ŀ¼ **/ 
		private String getDirectory() {
		 String dir = getSDPath() + "/" + CACHDIR; return dir; 
		}
		 /** ȡSD��·�� **/
		 private String getSDPath() { 
		File sdDir = null; 
		boolean sdCardExist = Environment.getExternalStorageState().equals( android.os.Environment.MEDIA_MOUNTED); 
		//�ж�sd���Ƿ���� 
		if (sdCardExist) {
		 sdDir = Environment.getExternalStorageDirectory(); 
		//��ȡ��Ŀ¼
		 }
		 if (sdDir != null) { 
		return sdDir.toString(); 
		} 
		else {
		 return ""; } } 
		/** * ����ļ�������޸�ʱ��������� */
		 private class FileLastModifSort implements Comparator {
			

			

			@Override
			public int compare(Object arg0, Object arg1) {
				// TODO Auto-generated method stub
				if (((File) arg0).lastModified() > ((File) arg1).lastModified()) { 
					return 1;
					 } else if (((File) arg0).lastModified() == ((File) arg1).lastModified()) {
					 return 0; 
					} else {
					 return -1;
					 }
			} 
		}
		}
