package com.jinanlongen.manatee.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jinanlongen.manatee.domain.Attributes;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月22日
 */
public interface AttributeRep extends JpaRepository<Attributes, Long> {
	public List<Attributes> findByAttributeValueId(long attributeValueId);

	@Transactional
	public void deleteByCategoryId(Long cid);

	@Transactional
	public void deleteByAttributeId(Long cid);

	@Transactional
	@Modifying
	@Query(value = "update jd_attributes set value=? where attribute_value_id=?", nativeQuery = true)
	public void updateAttr(String value, Long id);
}
