package com.jinanlongen.manatee.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinanlongen.manatee.domain.ColorAndSize;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月27日
 */
public interface ColorAndSizeRep extends JpaRepository<ColorAndSize, Long> {
	@Transactional
	public void deleteByCategoryId(Long id);

	@Transactional
	public void deleteByAttributeId(Long id);

}
