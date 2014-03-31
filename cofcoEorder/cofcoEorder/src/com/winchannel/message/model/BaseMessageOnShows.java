package com.winchannel.message.model;

import java.io.Serializable;
import java.util.Date;

/**
* 
* @author caishang
*/
public class BaseMessageOnShows  implements Serializable{

	private String title;
	private Long type;
	private Date createdby;
	private String content;
    private int id;
    private String  fileurl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Date getCreatedby() {
		return createdby;
	}
	public void setCreatedby(Date createdby) {
		this.createdby = createdby;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
    
    
}
