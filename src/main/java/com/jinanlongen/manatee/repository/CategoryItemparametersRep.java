package com.jinanlongen.manatee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinanlongen.manatee.domain.SnCategoryItemparameters;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月30日
 */
public interface CategoryItemparametersRep extends JpaRepository<SnCategoryItemparameters, Long> {
	List<SnCategoryItemparameters> findByCategoryCode(String code);
}
