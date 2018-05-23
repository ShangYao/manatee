package com.jinanlongen.manatee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinanlongen.manatee.domain.SnParOption;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月30日
 */
public interface SnParOptionRep extends JpaRepository<SnParOption, Long> {
	SnParOption findByParOptionCodeAndParOptionDesc(String code, String desc);

	SnParOption findByParOptionDescAndParCode(String code, String parCode);
}
