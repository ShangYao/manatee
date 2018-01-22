package com.jinanlongen.manatee.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

	@Transactional
	public void deleteByCategoryIdAndShopId(Long id, String shopid);

	@Query(value = "select JDCateId from LE_JDDK.dbo.LE_StoreTask a,LE_JDDK.dbo.DK_Category b where a.categoryId=b.categoryId and a.storetaskId=?", nativeQuery = true)
	public List<String> CategoryId(int id);

	@Query(value = "select storeId from LE_JDDK.dbo.LE_StoreTask a,LE_JDDK.dbo.DK_Category b where a.categoryId=b.categoryId and a.storetaskId=?", nativeQuery = true)
	public List<Integer> shopId(int id);
}
