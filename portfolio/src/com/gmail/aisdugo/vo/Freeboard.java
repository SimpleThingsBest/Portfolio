package com.gmail.aisdugo.vo;

import java.util.Date;

public class Freeboard {
	private int fno;
	private String ftitle;
	private String fcontent;
	private Date fdate;
	private long fcnt;
	private String fip;
	private String pid;
	private String pnick;
	private String sysdate;
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	public String getFtitle() {
		return ftitle;
	}
	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}
	public String getFcontent() {
		return fcontent;
	}
	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}
	public Date getFdate() {
		return fdate;
	}
	public void setFdate(Date fdate) {
		this.fdate = fdate;
	}
	public long getFcnt() {
		return fcnt;
	}
	public void setFcnt(long fcnt) {
		this.fcnt = fcnt;
	}
	public String getFip() {
		return fip;
	}
	public void setFip(String fip) {
		this.fip = fip;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPnick() {
		return pnick;
	}
	public void setPnick(String pnick) {
		this.pnick = pnick;
	}
	public String getSysdate() {
		return sysdate;
	}
	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}
	@Override
	public String toString() {
		return "Freeboard [fno=" + fno + ", ftitle=" + ftitle + ", fcontent=" + fcontent + ", fdate=" + fdate
				+ ", fcnt=" + fcnt + ", fip=" + fip + ", pid=" + pid + ", pnick=" + pnick + ", sysdate=" + sysdate
				+ "]";
	}
	
}
