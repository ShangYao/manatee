package com.jinanlongen.manatee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinanlongen.manatee.domain.SnItemparameters;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月30日
 */
public interface SnItemparametersRep extends JpaRepository<SnItemparameters, Long> {
	SnItemparameters findByParCode(String parCode);

	List<SnItemparameters> findByCategoryCode(String categoryCode);

}
