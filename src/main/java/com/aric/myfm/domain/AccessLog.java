/**
 * 
 */
package com.aric.myfm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.codehaus.jackson.map.annotate.JsonView;

/**
 * @author dursun
 *
 */
@javax.persistence.Entity
public class AccessLog implements Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8326306964148425609L;
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String fileName;
	
	@Column
	private String userName;
	
	@Column 
	private Date date;
	
	public AccessLog() {
		this.setDate(new Date());
	}
	
	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return id;
	}
	
	@JsonView(JsonViews.Admin.class)
	public String getFileName() {
		return fileName;
	}
	
	@JsonView(JsonViews.Admin.class)
	public String getUserName() {
		return userName;
	}

	@JsonView(JsonViews.Admin.class)
	public Date getDate() {
		return date;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFile(String fileName) {
		this.fileName = fileName;
	}
	
	
	public void setUser(String userName) {
		this.userName = userName;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

}
