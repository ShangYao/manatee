package com.jinanlongen.manatee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jinanlongen.manatee.domain.SnBrand;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月30日
 */
public interface SnBrandRep extends JpaRepository<SnBrand, Long> {
	@Query(value = " select count(*) from sn_brand where category_code=? and brand_code=? ", nativeQuery = true)
	int brandExist(String category, String brandCode);
}
