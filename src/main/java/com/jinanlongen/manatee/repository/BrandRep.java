package com.jinanlongen.manatee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jinanlongen.manatee.domain.Brand;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月22日
 */
@Service
public interface BrandRep extends JpaRepository<Brand, Long> {
	public Brand findByErpBrandId(long erpid);

}
