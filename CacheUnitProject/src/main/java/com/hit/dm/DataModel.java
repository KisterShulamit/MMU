package com.hit.dm;

/* implements the IDao interface */
public class DataModel<T> extends Object implements java.io.Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	/* The value of object*/
	private T content;
	
	/* The key of object*/
	private Long id;
	
	
	@Override
	/* Overrides the hashCode function of a class Object */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	/* Overrides the hashCode function of a class Object */
	/* Compares two objects of DataModel class*/
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataModel other = (DataModel) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			
			/* If they are not equal, returns false */
			return false;
		
		/* If they are equal, returns true */
		return true;
	}

	@Override
	/* Overrides the toString function of a class Object */
	/* Returns a string of a DataModel class object */
	public String toString() {
		return "DataModel [content=" + content + ", id=" + id + ", getId()=" + getId() + ", getContent()="
				+ getContent() + ", getDataModelId()=" + getDataModelId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/* C'tor */
	public DataModel(java.lang.Long id, T content) {
		this.content = content;
		this.id = id;
	}
	
	/* Default C'tor */
	public DataModel() {
		this.id=null;
		this.content = null;
	}

	/* Getters & Setters */
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	
	public T getContent() {
		return this.content;
	}

	public java.lang.Long getDataModelId() {
		return this.id;
	}
	

	void setContent(T content) {
		this.content = content;
	}

	void setDataModelId(java.lang.Long id) {
		this.id = id;
	}
}
