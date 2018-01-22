package com.jinanlongen.manatee.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jinanlongen.manatee.domain.ShopCategory;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月24日
 */
public interface ShopCategoryRep extends JpaRepository<ShopCategory, Long> {
	@Query(value = "select distinct shop_id from shop_category where lev='3' and id in(select min(id) from shop_category group by category_id)", nativeQuery = true)
	public List<String> findMinShopId();

	@Query(value = "select category_id from shop_category where lev='3' and id in(select min(id) from shop_category group by category_id) and shop_id=?", nativeQuery = true)
	public List<BigInteger> findMinCategorybyShopId(String shopId);

	@Query(value = "select distinct shop_id from shop_category where category_id=?", nativeQuery = true)
	public List<String> findShopIdByCategoryId(Long cid);

	@Query(value = "select distinct a.shop_id from shop_category a,category_attrs b where a.category_id=b.category_id and b.category_attr_id=?", nativeQuery = true)
	public List<String> findShopIdByCategoryAttrId(Long caid);

	@Query(value = "select distinct shop_id from shop_category where category_id=? and id in(select min(id) from shop_category group by category_id)", nativeQuery = true)
	public List<String> findMinShopIdByCid(Long cid);

}
