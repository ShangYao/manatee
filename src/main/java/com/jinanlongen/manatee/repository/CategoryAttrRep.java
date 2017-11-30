package com.jinanlongen.manatee.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jinanlongen.manatee.domain.CategoryAttrs;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月22日
 */
public interface CategoryAttrRep extends JpaRepository<CategoryAttrs, Long> {
	public CategoryAttrs findByCategoryAttrId(Long categoryAttrId);

	@Query(value = "select distinct category_attr_id  from category_attrs ", nativeQuery = true)
	public List<BigInteger> findAllAttrId();

	@Query(value = "select distinct category_attr_id  from category_attrs where category_attr_id not in(select distinct attribute_id from attributes) ", nativeQuery = true)
	public List<BigInteger> findOtherAttrId();

	// 非颜色，尺码属性
	@Query(value = "select distinct shop_id from (select *from category_attrs where attr_name <>'尺码' and  attr_name <>'颜色' and category_attr_id not in(select attribute_id from attributes))b", nativeQuery = true)
	public List<String> findSign();

	@Query(value = "select category_attr_id from (select *from category_attrs where  attr_name <>'尺码' and  attr_name <>'颜色' and category_attr_id not in(select attribute_id from attributes))b where shop_id=?", nativeQuery = true)
	public List<BigInteger> findCidBySign(String sign);

	// 颜色尺码属性
	@Query(value = "select distinct shop_id from (select distinct a.category_id,a.shop_id,b.category_attr_id,attr_name from shop_category a,category_attrs b where a.category_id=b.category_id and b.attr_name in('尺码','颜色') )c", nativeQuery = true)
	public List<String> findOtherSign();

	@Query(value = "select distinct shop_id from (select distinct a.category_id,a.shop_id,b.category_attr_id,attr_name from shop_category a,category_attrs b where a.category_id=b.category_id and a.category_id=? and b.attr_name in('尺码','颜色') )c", nativeQuery = true)
	public List<String> findOtherSignByCid(Long cid);

	@Query(value = "select distinct category_attr_id from (select distinct a.category_id,a.shop_id,b.category_attr_id,attr_name from shop_category a,category_attrs b where a.category_id=b.category_id and a.category_id=? and b.attr_name in('尺码','颜色') )c where shop_id=?", nativeQuery = true)
	public List<BigInteger> findOtherAttrByCidAndShopid(Long cid, String shopid);

	@Query(value = "select distinct category_attr_id from (select distinct a.category_id,a.shop_id,b.category_attr_id,attr_name from shop_category a,category_attrs b where a.category_id=b.category_id and b.attr_name in('尺码','颜色') )c where shop_id=?", nativeQuery = true)
	public List<BigInteger> findOtherCidByShopId(String shopid);

	@Transactional
	// @Query(value = "update category_attrs set have_attr_value=false where
	// category_attr_id=?1", nativeQuery = true)
	@Query("update CategoryAttrs u set u.haveAttrValue = false where categoryAttrId = ?1")
	public void updateHAV(Long category_attr_id);

	@Query(value = "select category_attr_id from category_attrs where category_id=?", nativeQuery = true)
	public List<BigInteger> findCAIdByCid(Long cid);

	@Query(value = "select category_attr_id from category_attrs where attr_name <>'尺码' and  attr_name <>'颜色' and category_id=?", nativeQuery = true)
	public List<BigInteger> findAttrbyCid(Long cid);

	@Transactional
	public void deleteByCategoryId(Long id);

}
