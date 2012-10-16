package com.spider.entity;

import java.util.Date;

public class StatusEntity {
	private int id;
	private String mkey;
	private String mval;
	private String descr;
	private Date cts;
	private Date uts;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMkey() {
		return mkey;
	}
	public void setMkey(String mkey) {
		this.mkey = mkey;
	}
	public String getMval() {
		return mval;
	}
	public void setMval(String mval) {
		this.mval = mval;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Date getCts() {
		return cts;
	}
	public void setCts(Date cts) {
		this.cts = cts;
	}
	public Date getUts() {
		return uts;
	}
	public void setUts(Date uts) {
		this.uts = uts;
	}
}
