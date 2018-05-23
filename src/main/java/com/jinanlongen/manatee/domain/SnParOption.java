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
 * @date 2018年3月30日
 */
@Entity
@Table(name = "sn_par_option")
public class SnParOption implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String parCode;
	private String parOptionCode;
	private String parOptionDesc;

	public String getParCode() {
		return parCode;
	}

	public void setParCode(String parCode) {
		this.parCode = parCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getParOptionCode() {
		return parOptionCode;
	}

	public void setParOptionCode(String parOptionCode) {
		this.parOptionCode = parOptionCode;
	}

	public String getParOptionDesc() {
		return parOptionDesc;
	}

	public void setParOptionDesc(String parOptionDesc) {
		this.parOptionDesc = parOptionDesc;
	}

}
