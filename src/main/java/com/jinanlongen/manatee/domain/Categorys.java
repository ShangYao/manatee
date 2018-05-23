package com.jinanlongen.manatee.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月22日
 */
@Entity
@Table(name = "jd_categorys")
public class Categorys {

	@Id
	private long id;
	private long categoryId;
	private long fid;
	private String name;
	private String status;
	private String lev;
	private long index_id;
	private Boolean is_parent;

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIs_parent() {
		return is_parent;
	}

	public void setIs_parent(Boolean is_parent) {
		this.is_parent = is_parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
	}

	public long getIndex_id() {
		return index_id;
	}

	public void setIndex_id(long index_id) {
		this.index_id = index_id;
	}

}
