package com.jinanlongen.manatee.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月30日
 */
@Entity
@Table(name = "sn_shop_category")
public class SnShopCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String firstCategoryName;
	private String categorySet;
	private String firstCategoryImg;
	private String firstCategorySort;
	private String firstCategoryCode;
	@OneToMany
	@JoinTable(name = "secondCategorys", joinColumns = { @JoinColumn(name = "cId") }, inverseJoinColumns = {
			@JoinColumn(name = "secondId") })
	private List<SnSecondCategory> secondCategorys;

	public List<SnSecondCategory> getSecondCategorys() {
		return secondCategorys;
	}

	public void setSecondCategorys(List<SnSecondCategory> secondCategorys) {
		this.secondCategorys = secondCategorys;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstCategoryName() {
		return firstCategoryName;
	}

	public void setFirstCategoryName(String firstCategoryName) {
		this.firstCategoryName = firstCategoryName;
	}

	public String getCategorySet() {
		return categorySet;
	}

	public void setCategorySet(String categorySet) {
		this.categorySet = categorySet;
	}

	public String getFirstCategoryImg() {
		return firstCategoryImg;
	}

	public void setFirstCategoryImg(String firstCategoryImg) {
		this.firstCategoryImg = firstCategoryImg;
	}

	public String getFirstCategorySort() {
		return firstCategorySort;
	}

	public void setFirstCategorySort(String firstCategorySort) {
		this.firstCategorySort = firstCategorySort;
	}

	public String getFirstCategoryCode() {
		return firstCategoryCode;
	}

	public void setFirstCategoryCode(String firstCategoryCode) {
		this.firstCategoryCode = firstCategoryCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
