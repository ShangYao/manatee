package com.jinanlongen.manatee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jinanlongen.manatee.domain.SnShopCategory;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月30日
 */
public interface SnShopCategoryRep extends JpaRepository<SnShopCategory, Long> {
	@Query(value = "select count(*) from lshop_category where first_category_code=?", nativeQuery = true)
	int categoryCodeExist(String code);

	SnShopCategory findByFirstCategoryCode(String code);
}
