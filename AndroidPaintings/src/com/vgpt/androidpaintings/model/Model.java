package com.vgpt.androidpaintings.model;

public interface Model<T> {

	public void save();

	public void update();

	public void delete();

	public String getTableName();

	public String getCreateTableSql();

}
