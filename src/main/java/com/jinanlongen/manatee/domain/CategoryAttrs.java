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
@Table(name = "jd_category_attrs")
public class CategoryAttrs {

	private long id;
	private long categoryId;
	private String attrName;
	private long attrValueIndexId;
	private String inputType;
	private String attributeType;
	@Id
	private long categoryAttrId;
	private String shopId;
	private boolean haveAttrValue;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public boolean isHaveAttrValue() {
		return haveAttrValue;
	}

	public void setHaveAttrValue(boolean haveAttrValue) {
		this.haveAttrValue = haveAttrValue;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getCategoryAttrId() {
		return categoryAttrId;
	}

	public void setCategoryAttrId(long categoryAttrId) {
		this.categoryAttrId = categoryAttrId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public long getAttrValueIndexId() {
		return attrValueIndexId;
	}

	public void setAttrValueIndexId(long attrValueIndexId) {
		this.attrValueIndexId = attrValueIndexId;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

}
