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
		CASRep.deleteByAttributeId(caid);
		CategoryAttrs categoryAttr = CARep.findOne(caid);
		if (categoryAttr != null) {
			if ("颜色".equals(categoryAttr.getAttrName()) || "尺码".equals(categoryAttr.getAttrName())) {
				CASRep.deleteByAttributeId(caid);
				List<String> shoplist = shopCategoryRep.findShopIdByCategoryId(caid);
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

	public void saveAttribute(List<BigInteger> list, String shopid, String attributeType) {
		Shop allshop = shopRep.findOne(shopid);
		JdUtils jd = new JdUtils(allshop);
		for (BigInteger attr : list) {
			List<CategoryAttrValueJos> attrList = jd.findValuesByAttrIdJos(attr.longValue());
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
		CASRep.deleteAll();
		List<String> list = CARep.findOtherSign();
		for (String shopid : list) {
			Shop shop = shopRep.findOne(shopid);
			JdUtils jdu = new JdUtils(shop);
			List<BigInteger> attrIdList = CARep.findOtherCidByShopId(shopid);
			System.out.println(shop.getName() + "店，更新数量：" + attrIdList.size());
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
		attrRep.deleteAll();
		List<String> list = CARep.findSign();
		for (String shopid : list) {
			Shop shop = shopRep.findOne(shopid);
			JdUtils jdu = new JdUtils(shop);
			List<BigInteger> attrIdList = CARep.findCidBySign(shopid);
			System.out.println(shopid + "店，更新数量：" + attrIdList.size());
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
		CARep.deleteAll();
		List<String> list = shopCategoryRep.findMinShopId();
		for (String shopid : list) {
			Shop shop = shopRep.findOne(shopid);
			JdUtils jdu = new JdUtils(shop);
			List<BigInteger> ll = shopCategoryRep.findMinCategorybyShopId(shopid);
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

	// 同步所有店铺的类目
	public String synAllCategory() {
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

		return "brand 同步成功";

	}

	public void saveBrand(VenderBrandPubInfo venderBrandPubInfo, String shopid) {
		Brand brand = new Brand();
		brand.setIsAutorization("true");
		brand.setBrandName(venderBrandPubInfo.getBrandName());
		brand.setErpBrandId(venderBrandPubInfo.getErpBrandId());
		brand.setShop_id(shopid);
		brandRep.save(brand);
	}

	// // 同步已授权的品牌
	// public String synBrand() {
	// List<VenderBrandPubInfo> brandlist = jdUtils.queryBrand();
	// if (brandlist.size() == 0) {
	// return "没有获取品牌";
	// } else {
	// brandRep.deleteAll();
	// for (int a = 0; a < brandlist.size(); a++) {
	// Brand brand = new Brand();
	// VenderBrandPubInfo venderBrandPubInfo = brandlist.get(a);
	// brand.setIsAutorization("true");
	// brand.setBrandName(venderBrandPubInfo.getBrandName());
	// brand.setErpBrandId(venderBrandPubInfo.getErpBrandId());
	// brandRep.save(brand);
	// }
	// return "同步了" + brandlist.size() + "条品牌信息";
	// }
	//
	// }

	public String deleteBrand() {
		brandRep.deleteAll();
		return "delete all";

	}

	// // 同步商家类目信息
	// public String synCategory() {
	// List<Category> categoryList = jdUtils.get();
	// if (categoryList.size() == 0) {
	// return "没有获取类目信息";
	// } else {
	// categoryRep.deleteAll();
	// for (int a = 0; a < categoryList.size(); a++) {
	// Categorys category = new Categorys();
	// Category JdCategory = categoryList.get(a);
	// category.setFid(JdCategory.getFid());
	// category.setIs_parent(JdCategory.isParent());
	// category.setLev(JdCategory.getLev() + "");
	// category.setName(JdCategory.getName());
	// category.setStatus(JdCategory.getStatus());
	// category.setIndex_id(JdCategory.getIndexId());
	// category.setCategoryId(JdCategory.getId());
	// categoryRep.save(category);
	// }
	// return "同步了" + categoryList.size() + "条商家类目信息";
	// }
	//
	// }

	// // 同步类目属性列表
	// public String synCategoryAttr() {
	// List<Long> idlist = categoryRep.findAllCategoryId();
	// int num = 0;
	// if (idlist.size() != 0) {
	// CARep.deleteAll();
	// }
	// for (int a = 0; a < idlist.size(); a++) {
	// List<CategoryAttr> calist = jdUtils.findAttrsByCategoryId(idlist.get(a));
	// num += calist.size();
	// if (calist == null || calist.size() == 0) {
	// continue;
	// } else {
	// for (int b = 0; b < calist.size(); b++) {
	// CategoryAttrs categoryAttrs = new CategoryAttrs();
	// CategoryAttr jdcategoryAttr = calist.get(b);
	// categoryAttrs.setAttrName(jdcategoryAttr.getAttName());
	// categoryAttrs.setCategoryAttrId(jdcategoryAttr.getCategoryAttrId());
	// categoryAttrs.setInputType(jdcategoryAttr.getInputType() + "");
	// categoryAttrs.setAttributeType(jdcategoryAttr.getAttributeType() + "");
	// categoryAttrs.setCategoryId(jdcategoryAttr.getCategoryId());
	// CARep.save(categoryAttrs);
	// }
	// }
	// }
	// return "同步完成" + num + "条类目属性信息";
	//
	// }

	// // 类目属性值
	// public String synAttribute() {
	// List<BigInteger> idlist = CARep.findAllAttrId();
	// if (idlist.size() == 0) {
	// return "没有需同步类目属性值";
	// } else {
	// attrRep.deleteAll();
	// int attrsize = 0;
	// for (int a = 0; a < idlist.size(); a++) {
	// List<CategoryAttrValueJos> attrList =
	// jdUtils.findValuesByAttrIdJos(idlist.get(a).longValue());
	// attrsize += attrList.size();
	// if (attrList == null || attrList.size() == 0) {
	// continue;
	// } else {
	// for (int b = 0; b < attrList.size(); b++) {
	// Attributes attr = new Attributes();
	// CategoryAttrValueJos jdattr = attrList.get(b);
	// attr.setAttributeId(jdattr.getAttributeId());
	// attr.setValue(jdattr.getValue());
	// attr.setAttributeValueId(jdattr.getId());
	// attr.setIndexId(jdattr.getIndexId());
	// attr.setCategoryId(jdattr.getCategoryId());
	// attrRep.save(attr);
	// }
	// }
	//
	// }
	// return "恭喜你，同步完成！！" + attrsize + "条属性";
	// }
	//
	// }

	// // 其他类目属性值
	// public String synOtherAttribute() {
	// List<BigInteger> idlist = CARep.findOtherAttrId();
	// if (idlist.size() == 0) {
	// return "没有需同步类目属性值";
	// } else {
	// int attrsize = 0;
	// for (int a = 0; a < idlist.size(); a++) {
	// List<CategoryAttrValueJos> attrList =
	// jdUtils.findValuesByAttrIdJos(idlist.get(a).longValue());
	// attrsize += attrList.size();
	// if (attrList == null || attrList.size() == 0) {
	// continue;
	// } else {
	// for (int b = 0; b < attrList.size(); b++) {
	// Attributes attr = new Attributes();
	// CategoryAttrValueJos jdattr = attrList.get(b);
	// attr.setAttributeId(jdattr.getAttributeId());
	// attr.setValue(jdattr.getValue());
	// attr.setAttributeValueId(jdattr.getId());
	// attr.setIndexId(jdattr.getIndexId());
	// attr.setCategoryId(jdattr.getCategoryId());
	// attrRep.save(attr);
	// }
	// }
	//
	// }
	// return "恭喜你，同步完成！！" + attrsize + "条属性";
	// }
	//
	// }

	// public String synAttributeone() {
	//
	// Long id = (long) 103008;
	// List<CategoryAttrValueJos> attrList = jdUtils.findValuesByAttrIdJos(id);
	// if (attrList == null || attrList.size() == 0) {
	// return "sshibai";
	// } else {
	// for (int b = 0; b < attrList.size(); b++) {
	// Attributes attr = new Attributes();
	// CategoryAttrValueJos jdattr = attrList.get(b);
	// Attributes newAttr = attrRep.findByAttributeValueId(jdattr.getId());
	// if (newAttr != null) {
	// System.out.println("newAttr != null");
	// attr.setId(newAttr.getId());
	// }
	// attr.setAttributeId(jdattr.getAttributeId());
	// attr.setValue(jdattr.getValue());
	// attr.setAttributeValueId(jdattr.getId());
	// attr.setIndexId(jdattr.getIndexId());
	// attr.setCategoryId(jdattr.getCategoryId());
	// attrRep.save(attr);
	// }
	// }
	//
	// return "恭喜你，同步完成！！";
	//
	// }
}
