package com.iFundi.models;

import javax.persistence.*;

/**
 * 
 * @author Cyrus
 *
 */

@MappedSuperclass
public abstract class BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	//private String id;
	
	
	protected BaseModel() {
		id=null;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id =id;
	}
	
}
