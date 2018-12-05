package com.gmail.aisdugo.vo;

import java.util.Arrays;
import java.util.Date;

public class Peoples {
	private String pid;
	private String 	ppw;
	private String pnick;
	private String pimage;
	private Date updatedate;
	private Date logindate;
	private byte [] byteimage;
	

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPpw() {
		return ppw;
	}
	public void setPpw(String ppw) {
		this.ppw = ppw;
	}
	public String getPnick() {
		return pnick;
	}
	public void setPnick(String pnick) {
		this.pnick = pnick;
	}
	public String getPimage() {
		return pimage;
	}
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public Date getLogindate() {
		return logindate;
	}
	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}
	public byte[] getByteimage() {
		return byteimage;
	}
	public void setByteimage(byte[] byteimage) {
		this.byteimage = byteimage;
	}
	@Override
	public String toString() {
		return "Peoples [pid=" + pid + ", ppw=" + ppw + ", pnick=" + pnick + ", pimage=" + pimage + ", updatedate="
				+ updatedate + ", logindate=" + logindate + ", byteimage=" + Arrays.toString(byteimage) + "]";
	}
	

}
