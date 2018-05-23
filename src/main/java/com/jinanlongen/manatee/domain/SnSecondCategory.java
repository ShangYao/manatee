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
@Table(name = "sn_shop_seond_category")
public class SnSecondCategory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String secondCategoryCode;
	private String secondCategoryName;
	private String secondCategorySort;
	private String secondCategoryImg;

	public String getSecondCategoryName() {
		return secondCategoryName;
	}

	public void setSecondCategoryName(String secondCategoryName) {
		this.secondCategoryName = secondCategoryName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSecondCategoryCode() {
		return secondCategoryCode;
	}

	public void setSecondCategoryCode(String secondCategoryCode) {
		this.secondCategoryCode = secondCategoryCode;
	}

	public String getSecondCategorySort() {
		return secondCategorySort;
	}

	public void setSecondCategorySort(String secondCategorySort) {
		this.secondCategorySort = secondCategorySort;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSecondCategoryImg() {
		return secondCategoryImg;
	}

	public void setSecondCategoryImg(String secondCategoryImg) {
		this.secondCategoryImg = secondCategoryImg;
	}
}
