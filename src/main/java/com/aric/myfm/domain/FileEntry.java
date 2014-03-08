/**
 * 
 */
package com.aric.myfm.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.codehaus.jackson.map.annotate.JsonView;

/**
 * @author dursun
 *
 */
@javax.persistence.Entity
public class FileEntry implements Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5473833570928576996L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private String address;

	public FileEntry() {
	}


	@JsonView(JsonViews.User.class)
	public Long getId() {

		return this.id;
	}


	@JsonView(JsonViews.User.class)
	public String getName() {
		return this.name;
	}


	public void setName(String name) {

		this.name = name;
	}

	@JsonView(JsonViews.User.class)
	public String getAddress() {
		return this.address;
	}
	
	
	public void setAddress(String address) {
		
		this.address = address;
	}

}
