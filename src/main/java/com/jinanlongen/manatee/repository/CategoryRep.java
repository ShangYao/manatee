package com.jinanlongen.manatee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.jinanlongen.manatee.domain.Categorys;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月22日
 */
@Service
public interface CategoryRep extends JpaRepository<Categorys, Long> {
	public Categorys findByCategoryId(String categoryId);

	@Query("select distinct categoryId from Categorys")
	public List<Long> findAllCategoryId();

}
