package com.jinanlongen.manatee.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月30日
 */
@Entity
public class SnCategoryItemparameters implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String categoryCode;
	private long itemParametersId;
	private String paraTemplateCode;

	public String getParaTemplateCode() {
		return paraTemplateCode;
	}

	public void setParaTemplateCode(String paraTemplateCode) {
		this.paraTemplateCode = paraTemplateCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public long getItemParametersId() {
		return itemParametersId;
	}

	public void setItemParametersId(long itemParametersId) {
		this.itemParametersId = itemParametersId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
