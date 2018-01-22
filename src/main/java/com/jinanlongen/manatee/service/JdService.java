package com.jinanlongen.manatee.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.domain.category.Category;
import com.jd.open.api.sdk.domain.list.CategoryAttrReadService.CategoryAttr;
import com.jd.open.api.sdk.domain.list.CategoryAttrValueReadService.CategoryAttrValueJos;
import com.jd.open.api.sdk.response.list.VenderBrandPubInfo;
import com.jinanlongen.manatee.domain.Attributes;
import com.jinanlongen.manatee.domain.Brand;
import com.jinanlongen.manatee.domain.CategoryAttrs;
import com.jinanlongen.manatee.domain.Categorys;
import com.jinanlongen.manatee.domain.ColorAndSize;
import com.jinanlongen.manatee.domain.Shop;
import com.jinanlongen.manatee.domain.ShopCategory;
import com.jinanlongen.manatee.repository.AttributeRep;
import com.jinanlongen.manatee.repository.BrandRep;
import com.jinanlongen.manatee.repository.CategoryAttrRep;
import com.jinanlongen.manatee.repository.CategoryRep;
import com.jinanlongen.manatee.repository.ColorAndSizeRep;
import com.jinanlongen.manatee.repository.ShopCategoryRep;
import com.jinanlongen.manatee.repository.ShopRep;
import com.jinanlongen.manatee.utils.JdUtils;

/**
 * 类说明
 * 
 * @author shangyaos
 * @date 2017年11月22日
 */
@Service
public class JdService {

	@Autowired
	private BrandRep brandRep;
	@Autowired
	private CategoryRep categoryRep;
	@Autowired
	private CategoryAttrRep CARep;
	@Autowired
	private AttributeRep attrRep;
	@Autowired
	private ShopRep shopRep;
	@Autowired
	private ShopCategoryRep shopCategoryRep;
	@Autowired
	private ColorAndSizeRep CASRep;

	public void test() {
		System.out.println("jdtest");
		List<String> list = shopCategoryRep.findMinShopId();
		list.forEach(n -> System.out.println(n));
	}

	// 通过任务ID更新颜色尺码
	public void updateByDutyId(int id) {
		List<String> cid = CASRep.CategoryId(id);
		List<Integer> shopid = CASRep.shopId(id);
		if (cid.size() == 1 && shopid.size() == 1) {
			System.out.println(updateCategory(Long.valueOf(cid.get(0)), shopid.get(0) + ""));
		}
	}

	// 更新一个非ColorAndSize属性
	public String updateAttrValue(Long id, String shopid) {
		if ("".equals(shopid) || shopid == null) {
			List<Shop> list = shopRep.findAll();
			for (Shop shop : list) {
				JdUtils jd = new JdUtils(shop);
				CategoryAttrValueJos categoryAttrValue = jd.findValuesByIdJos(id);
				if (categoryAttrValue != null) {
					attrRep.updateAttr(categoryAttrValue.getValue(), id);
				}
			}
		} else {
			Shop allshop = shopRep.findOne(shopid);
			JdUtils jd = new JdUtils(allshop);
			CategoryAttrValueJos categoryAttrValue = jd.findValuesByIdJos(id);
			if (categoryAttrValue != null) {
				attrRep.updateAttr(categoryAttrValue.getValue(), id);
			}
		}

		return "同步了属性，Id：" + id;
	}

	// 更新一个ColorAndSize属性
	public String updateColorAndSizeValue(Long id) {
		ColorAndSize colorAndSize = CASRep.findOne(id);
		if (colorAndSize != null) {
			Shop allshop = shopRep.findOne(colorAndSize.getShopId());
			JdUtils jd = new JdUtils(allshop);
			CategoryAttrValueJos categoryAttrValue = jd.findValuesByIdJos(id);
			saveColorAndSize(categoryAttrValue, colorAndSize.getShopId());
		} else {
			List<Shop> list = shopRep.findAll();
			for (Shop shop : list) {
				JdUtils jd = new JdUtils(shop);
				CategoryAttrValueJos categoryAttrValue = jd.findValuesByIdJos(id);
				if (categoryAttrValue != null) {
					saveColorAndSize(categoryAttrValue, shop.getShopId());
					break;
				}
			}
		}

		return "同步了颜色尺码，Id：" + id;
	}

	// 更新一个类目属性的属性值
	public String updateCategoryAttr(Long caid) {
		CategoryAttrs categoryAttr = CARep.findOne(caid);
		if (categoryAttr != null) {
			if ("颜色".equals(categoryAttr.getAttrName()) || "尺码".equals(categoryAttr.getAttrName())) {
				CASRep.deleteByAttributeId(caid);
				List<String> shoplist = shopCategoryRep.findShopIdByCategoryAttrId(caid);
				for (String shop_id : shoplist) {
					saveAttribute(longToBigIntegerList(caid), shop_id, "ColorAndSize");
				}
			} else {
				attrRep.deleteByAttributeId(caid);
				saveAttribute(longToBigIntegerList(caid), categoryAttr.getShopId(), "other");
			}
		} else {
			return "categoryAttr = null";
		}
		return "类目属性同步成功";

	}

	public List<BigInteger> longToBigIntegerList(Long id) {
		List<BigInteger> attrIdList = new ArrayList<BigInteger>();
		BigInteger bigint = new BigInteger(id.toString());
		attrIdList.add(bigint);
		return attrIdList;

	}

	// 更新一个类目的类目属性及属性值
	// 查找类目属性表，得到shop——id，更新类目属性表;更新通用属性；颜色尺码属性
	public String updateCategory(Long cid) {

		CARep.deleteByCategoryId(cid);

		List<String> list = shopCategoryRep.findMinShopIdByCid(cid);
		String shopid = list.get(0);
		Shop shop = shopRep.findOne(shopid);
		JdUtils jdu = new JdUtils(shop);
		List<CategoryAttr> calist = jdu.findAttrsByCategoryId(cid);
		System.out.println(shopid + "店，更新数量：" + calist.size());
		if (calist != null && calist.size() != 0) {
			calist.forEach((jdcategoryAttr) -> saveCategoryAttrs(jdcategoryAttr, shopid));
		}
		// 删除属性
		attrRep.deleteByCategoryId(cid);
		// 非颜色尺码属性
		List<BigInteger> attrIdList = CARep.findAttrbyCid(cid);
		System.out.println(shopid + "店，更新数量：" + attrIdList.size());
		saveAttribute(attrIdList, shopid, "other");

		CASRep.deleteByCategoryId(cid);
		// 颜色尺码属性
		List<String> shoplist = CARep.findOtherSignByCid(cid);
		for (String shop_id : shoplist) {
			List<BigInteger> attrId = CARep.findOtherAttrByCidAndShopid(cid, shop_id);
			saveAttribute(attrId, shop_id, "ColorAndSize");

		}
		return "同步类目成功，类目ID：" + cid;
	}

	// 同步指定店铺的指定类目的颜色尺码属性
	public String updateCategory(Long cid, String shop_id) {
		CASRep.deleteByCategoryIdAndShopId(cid, shop_id);
		// 颜色尺码属性
		List<BigInteger> attrId = CARep.findOtherAttrByCidAndShopid(cid, shop_id);
		saveAttribute(attrId, shop_id, "ColorAndSize");
		return "同步类目成功，类目ID：" + cid + "shop:" + shop_id;
	}

	public void saveAttribute(List<BigInteger> list, String shopid, String attributeType) {
		Shop allshop = shopRep.findOne(shopid);
		JdUtils jd = new JdUtils(allshop);
		for (BigInteger attr : list) {
			List<CategoryAttrValueJos> attrList = jd.findValuesByAttrIdJos(attr.longValue());
			System.out.println(attrList.size());
			if (attrList == null || attrList.size() == 0) {
				continue;
			} else {
				for (CategoryAttrValueJos jdattr : attrList) {
					if ("ColorAndSize".equals(attributeType)) {
						saveColorAndSize(jdattr, shopid);
					} else {
						saveAttr(jdattr, shopid);
					}
				}
			}
		}
	}

	// 同步所有店铺颜色尺码属性
	public String synColorAndSize() {
		System.out.println("开始同步所有店铺的颜色尺码属性信息");
		CASRep.deleteAll();
		List<String> list = CARep.findOtherSign();
		for (String shopid : list) {
			Shop shop = shopRep.findOne(shopid);
			JdUtils jdu = new JdUtils(shop);
			List<BigInteger> attrIdList = CARep.findOtherCidByShopId(shopid);
			System.out.println(shop.getName() + "店，更新颜色尺码属性数量：" + attrIdList.size());
			for (BigInteger attrId : attrIdList) {
				List<CategoryAttrValueJos> attrList = jdu.findValuesByAttrIdJos(attrId.longValue());
				if (attrList == null || attrList.size() == 0) {
					continue;
				} else {
					for (CategoryAttrValueJos jdattr : attrList) {
						saveColorAndSize(jdattr, shopid);
					}
				}
			}
		}
		return "同步颜色尺码属性完成！！";
	}

	public void saveColorAndSize(CategoryAttrValueJos jdattr, String shopid) {
		ColorAndSize cas = new ColorAndSize();
		cas.setAttributeId(jdattr.getAttributeId());
		cas.setValue(jdattr.getValue());
		cas.setAttributeValueId(jdattr.getId());
		cas.setIndexId(jdattr.getIndexId());
		cas.setCategoryId(jdattr.getCategoryId());
		cas.setShopId(shopid);
		CASRep.save(cas);
	}

	// 同步所有店铺非颜色，尺码属性信息
	public String synAllAtrribute() {
		System.out.println("开始同步所有店铺的通用属性信息");
		attrRep.deleteAll();
		List<String> list = CARep.findSign();
		for (String shopid : list) {
			Shop shop = shopRep.findOne(shopid);
			JdUtils jdu = new JdUtils(shop);
			List<BigInteger> attrIdList = CARep.findCidBySign(shopid);
			System.out.println(shop.getName() + "店，更新通用属性信息数量：" + attrIdList.size());
			for (BigInteger attrId : attrIdList) {
				List<CategoryAttrValueJos> attrList = jdu.findValuesByAttrIdJos(attrId.longValue());
				if (attrList == null || attrList.size() == 0) {
					continue;
				} else {
					for (CategoryAttrValueJos jdattr : attrList) {
						saveAttr(jdattr, shopid);
					}
				}
			}
		}
		return "同步通用属性完成!!  ";
	}

	public void saveAttr(CategoryAttrValueJos jdattr, String shopid) {
		Attributes attr = new Attributes();
		attr.setAttributeId(jdattr.getAttributeId());
		attr.setValue(jdattr.getValue());
		attr.setAttributeValueId(jdattr.getId());
		attr.setIndexId(jdattr.getIndexId());
		attr.setCategoryId(jdattr.getCategoryId());
		attr.setSign(shopid);
		attrRep.save(attr);
	}

	// 同步一个类目 所有店铺的类目属性
	public String synOneCategory(Long cid) {
		List<String> list = shopCategoryRep.findMinShopIdByCid(cid);
		Shop shop = shopRep.findOne(list.get(0));
		JdUtils jdu = new JdUtils(shop);
		List<CategoryAttr> calist = jdu.findAttrsByCategoryId(cid);
		if (calist != null && calist.size() != 0) {
			for (CategoryAttr jdcategoryAttr : calist) {
				saveCategoryAttrs(jdcategoryAttr, list.get(0));
			}
		}
		List<BigInteger> attrIdList = CARep.findCAIdByCid(cid);
		for (BigInteger attrId : attrIdList) {
			List<CategoryAttrValueJos> attrList = jdu.findValuesByAttrIdJos(attrId.longValue());
			if (attrList == null || attrList.size() == 0) {
				continue;
			} else {
				for (CategoryAttrValueJos jdattr : attrList) {
					saveAttr(jdattr, list.get(0));
				}
			}
		}

		return "同步类目属性成功!!  ";
	}

	// 同步所有店铺的类目属性
	public String synAllcategoryAttr() {
		System.out.println("开始同步所有店铺的类目属性信息");
		CARep.deleteAll();
		List<String> list = shopCategoryRep.findMinShopId();
		for (String shopid : list) {
			Shop shop = shopRep.findOne(shopid);
			JdUtils jdu = new JdUtils(shop);
			List<BigInteger> ll = shopCategoryRep.findMinCategorybyShopId(shopid);
			System.out.println(shop.getName() + "店，更新类目属性信息数量：" + ll.size());
			for (BigInteger CategoryId : ll) {
				List<CategoryAttr> calist = jdu.findAttrsByCategoryId(CategoryId.longValue());
				if (calist == null || calist.size() == 0) {
					continue;
				} else {
					for (CategoryAttr jdcategoryAttr : calist) {
						saveCategoryAttrs(jdcategoryAttr, shopid);
					}
				}
			}
		}
		return "同步类目属性成功!!  ";
	}

	public void saveCategoryAttrs(CategoryAttr jdcategoryAttr, String shopid) {
		CategoryAttrs categoryAttrs = new CategoryAttrs();
		categoryAttrs.setAttrName(jdcategoryAttr.getAttName());
		categoryAttrs.setCategoryAttrId(jdcategoryAttr.getCategoryAttrId());
		categoryAttrs.setInputType(jdcategoryAttr.getInputType() + "");
		categoryAttrs.setAttributeType(jdcategoryAttr.getAttributeType() + "");
		categoryAttrs.setCategoryId(jdcategoryAttr.getCategoryId());
		// categoryAttrs.setAttrValueIndexId(jdcategoryAttr.attrv);
		categoryAttrs.setShopId(shopid);
		// categoryAttrs.setHaveAttrValue(true);
		CARep.save(categoryAttrs);
	}

	// 同步指定店铺的类目信息
	public String synOneShop(String id) {
		System.out.println("开始同步所有店铺的类目信息");
		// shopCategoryRep.deleteAll();
		// categoryRep.deleteAll();

		Shop shop = shopRep.findOne(id);
		JdUtils jdu = new JdUtils(shop);
		List<Category> categoryList = jdu.get();
		System.out.println(shop.getName() + "---开通了类目数量" + categoryList.size());
		if (categoryList == null || categoryList.size() == 0) {
			return "无类目";
		} else {
			for (Category jdcategory : categoryList) {
				// 商店类目对应表
				ShopCategory shopMap = new ShopCategory();
				shopMap.setCategoryId((long) jdcategory.getId());
				shopMap.setShopId(shop.getShopId());
				shopMap.setLev(jdcategory.getLev() + "");
				shopCategoryRep.save(shopMap);

				if (categoryRep.exists((long) jdcategory.getId())) {
					continue;
				} else {
					saveCategory(jdcategory);
				}
			}
		}
		return "jkjk";

	}

	// 同步所有店铺的类目
	public String synAllCategory() {
		System.out.println("开始同步所有店铺的类目信息");
		shopCategoryRep.deleteAll();
		categoryRep.deleteAll();
		List<Shop> list = shopRep.findAll();
		for (Shop shop : list) {
			JdUtils jdu = new JdUtils(shop);
			List<Category> categoryList = jdu.get();
			if (categoryList == null || categoryList.size() == 0) {
				continue;
			} else {
				for (Category jdcategory : categoryList) {
					// 商店类目对应表
					ShopCategory shopMap = new ShopCategory();
					shopMap.setCategoryId((long) jdcategory.getId());
					shopMap.setShopId(shop.getShopId());
					shopMap.setLev(jdcategory.getLev() + "");
					shopCategoryRep.save(shopMap);

					if (categoryRep.exists((long) jdcategory.getId())) {
						continue;
					} else {
						saveCategory(jdcategory);
					}
				}
			}
		}
		return "同步类目成功!!  ";
	}

	public void saveCategory(Category jdcategory) {
		Categorys category = new Categorys();
		category.setFid(jdcategory.getFid());
		category.setIs_parent(jdcategory.isParent());
		category.setLev(jdcategory.getLev() + "");
		category.setName(jdcategory.getName());
		category.setStatus(jdcategory.getStatus());
		category.setIndex_id(jdcategory.getIndexId());
		category.setId((long) jdcategory.getId());
		categoryRep.save(category);
	}

	// 同步所有品牌
	public String synAllbrand() {
		System.out.println("开始同步所有店铺的品牌信息");
		brandRep.deleteAll();
		List<Shop> list = shopRep.findAll();
		for (Shop shop : list) {
			JdUtils jdu = new JdUtils(shop);
			List<VenderBrandPubInfo> brandlist = jdu.queryBrand();
			if (brandlist.size() == 0) {
				continue;
			} else {
				for (VenderBrandPubInfo venderBrandPubInfo : brandlist) {
					saveBrand(venderBrandPubInfo, shop.getShopId());
				}
			}
		}

		return "品牌信息同步成功";

	}

	public void saveBrand(VenderBrandPubInfo venderBrandPubInfo, String shopid) {
		Brand brand = new Brand();
		brand.setIsAutorization("true");
		brand.setBrandName(venderBrandPubInfo.getBrandName());
		brand.setErpBrandId(venderBrandPubInfo.getErpBrandId());
		brand.setShop_id(shopid);
		brandRep.save(brand);
	}

}
