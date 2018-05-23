package com.jinanlongen.manatee.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月30日
 */
@Entity
@Table(name = "sn_parameter")
public class SnItemparameters {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String categoryCode;
	private String paraTemplateCode;
	private String paraTemplateDesc;
	// @Column(unique = true)
	private String parCode;
	private String parName;
	private String parType;
	private String parUnit;
	private String isMust;
	private String dataType;

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

	public String getParaTemplateCode() {
		return paraTemplateCode;
	}

	public void setParaTemplateCode(String paraTemplateCode) {
		this.paraTemplateCode = paraTemplateCode;
	}

	public String getParaTemplateDesc() {
		return paraTemplateDesc;
	}

	public void setParaTemplateDesc(String paraTemplateDesc) {
		this.paraTemplateDesc = paraTemplateDesc;
	}

	public String getParCode() {
		return parCode;
	}

	public void setParCode(String parCode) {
		this.parCode = parCode;
	}

	public String getParName() {
		return parName;
	}

	public void setParName(String parName) {
		this.parName = parName;
	}

	public String getParType() {
		return parType;
	}

	public void setParType(String parType) {
		this.parType = parType;
	}

	public String getParUnit() {
		return parUnit;
	}

	public void setParUnit(String parUnit) {
		this.parUnit = parUnit;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
