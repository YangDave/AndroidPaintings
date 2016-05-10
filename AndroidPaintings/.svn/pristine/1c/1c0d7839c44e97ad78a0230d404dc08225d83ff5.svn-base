package com.vgpt.androidpaintings.model;

import java.util.Date;

import android.database.Cursor;

import com.vgpt.androidpaintings.db.RowMapper;


public class PicFileName extends AbstractModel<PicFileName> {
	private int id;
	private int picId;
	
	private String picFileName;
	
	private int gmtCreatTime;
	private int gmtUpdateTime;
	
	@SuppressWarnings("deprecation")
	public PicFileName(int picId,String picFileName){
		
		this.picId=picId;
		this.picFileName=picFileName;
		this.gmtCreatTime=new Date().getSeconds();
		this.gmtUpdateTime=new Date().getSeconds();
	}
	
	public PicFileName(){
		
		
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPicId() {
		return picId;
	}

	public void setPicId(int picId) {
		this.picId = picId;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public int getGmtCreatTime() {
		return gmtCreatTime;
	}

	public void setGmtCreatTime(int gmtCreatTime) {
		this.gmtCreatTime = gmtCreatTime;
	}

	public int getGmtUpdateTime() {
		return gmtUpdateTime;
	}

	public void setGmtUpdateTime(int gmtUpdateTime) {
		this.gmtUpdateTime = gmtUpdateTime;
	}

	@Override
	public void save() {
		String sql = "insert into pic_file_name_info (pic_id,pic_file_name,gmt_creat_time,gmt_update_time) values(?,?,?,?)";
		getJDBCTemplate().execute(sql, this.picId,this.picFileName,this.gmtCreatTime,this.gmtUpdateTime);
		setInsertId();
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void update() {
		if (this.id > 0) {

			String sql = "update pic_file_name_info set pic_id=? , pic_file_name=? , gmt_update_time= ?  where id="
					+ this.id;

			getJDBCTemplate().execute(sql, this.picId, this.picFileName,
					new Date().getSeconds());

		}
	}

	@Override
	public void delete() {
		
		if(this.id>0){
			
			String sql="delete from pic_file_name where id=?";
			getJDBCTemplate().execute(sql, this.id);
		}
		
	}

	
	public static PicFileName queryObjectByPicId(int picId){
		String sql="select * from pic_file_name where pic_id=?";
		return getJDBCTemplate().queryForObject(sql, getRowMapper(), picId+"");
		
	}
	
	
	
	public static PicFileNameRowMapper getRowMapper(){
		
		return  new PicFileNameRowMapper();
	}
	@Override
	public String getTableName() {
		return "pic_file_name_info";
	}

	@Override
	public String getCreateTableSql() {
		return null;
	}
	private void setInsertId() {

		String sql = "select last_insert_rowid() from pic_file_name_info";

		Cursor cursor = getJDBCTemplate().getDbHelper().getReadableDatabase()
				.rawQuery(sql, null);
		if (cursor.moveToFirst())
			this.id = cursor.getInt(0);

	}
	
	
	
	
}
class PicFileNameRowMapper implements RowMapper<PicFileName> {

	@Override
	public PicFileName forEachRow(Cursor c, int row) {
		
		PicFileName f=new PicFileName();
		f.setId(c.getInt(c.getColumnIndex("id")));
		f.setPicFileName(c.getString(c.getColumnIndex("pic_file_name")));
		f.setGmtCreatTime(c.getInt(c.getColumnIndex("gmt_creat_time")));
		f.setGmtUpdateTime(c.getInt(c.getColumnIndex("gmt_update_time")));
		return f;
	}
	
	
}

