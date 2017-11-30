package com.jinanlongen.manatee.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月22日
 */
@Entity
public class Brand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String brandName;
	private long erpBrandId;
	private String code;
	private String isAutorization;
	private String shop_id;

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public Long getId() {
		return id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public long getErpBrandId() {
		return erpBrandId;
	}

	public void setErpBrandId(long erpBrandId) {
		this.erpBrandId = erpBrandId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsAutorization() {
		return isAutorization;
	}

	public void setIsAutorization(String isAutorization) {
		this.isAutorization = isAutorization;
	}
}
