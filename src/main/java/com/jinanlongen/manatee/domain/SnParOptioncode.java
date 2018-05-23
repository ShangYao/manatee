package com.jinanlongen.manatee.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年4月28日
 */
@Entity
@Table(name = "sn_par_optioncode")
public class SnParOptioncode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private long parOptionId;
	private String paraTemplateCode;

	public long getParOptionId() {
		return parOptionId;
	}

	public void setParOptionId(long parOptionId) {
		this.parOptionId = parOptionId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getParaTemplateCode() {
		return paraTemplateCode;
	}

	public void setParaTemplateCode(String paraTemplateCode) {
		this.paraTemplateCode = paraTemplateCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
