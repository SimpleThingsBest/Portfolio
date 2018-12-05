package com.gmail.aisdugo.vo;

public class Mfds {
	private String title;
	private String content;
	private String link;
	private String pubdate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	@Override
	public String toString() {
		return "Mfds [title=" + title + ", content=" + content + ", link=" + link + ", pubdate=" + pubdate + "]";
	}
	
	
}
