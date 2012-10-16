package com.spider.entity;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-26
 * Time: 13:12:38
 * To change this template use File | Settings | File Templates.
 */
public class DomainEntity {
	private int id;
	private String name;
	private String org;
	private int scanCount;
	private Date lastScanDate;
	private Date registDate;
	private Date overDate;
	private String owner;
	private int scroe;
	private boolean isWord;
	private Date cts;
	private Date uts;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public int getScanCount() {
		return scanCount;
	}
	public void setScanCount(int scanCount) {
		this.scanCount = scanCount;
	}
	public Date getLastScanDate() {
		return lastScanDate;
	}
	public void setLastScanDate(Date lastScanDate) {
		this.lastScanDate = lastScanDate;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public Date getOverDate() {
		return overDate;
	}
	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getScroe() {
		return scroe;
	}
	public void setScroe(int scroe) {
		this.scroe = scroe;
	}
	public boolean isWord() {
		return isWord;
	}
	public void setWord(boolean isWord) {
		this.isWord = isWord;
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
