package com.jinanlongen.manatee.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年4月13日
 */
@Entity
public class SnNation {
	@Id
	private String nationCode;
	private String nationName;

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

}
