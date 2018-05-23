package com.jinanlongen.manatee.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月22日
 */
@Entity
@Table(name = "jd_attributes")
public class Attributes implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private long attributeId;
	private long categoryId;
	private int indexId;
	private String value;
	private long attributeValueId;
	private String haveAttrValue;
	private String sign;

	public String getHaveAttrValue() {
		return haveAttrValue;
	}

	public void setHaveAttrValue(String haveAttrValue) {
		this.haveAttrValue = haveAttrValue;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(long attributeId) {
		this.attributeId = attributeId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getAttributeValueId() {
		return attributeValueId;
	}

	public void setAttributeValueId(long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
