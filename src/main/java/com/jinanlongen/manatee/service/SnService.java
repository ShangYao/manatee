package com.jinanlongen.manatee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jinanlongen.manatee.domain.SnBrand;
import com.jinanlongen.manatee.domain.SnCategory;
import com.jinanlongen.manatee.domain.SnCategoryItemparameters;
import com.jinanlongen.manatee.domain.SnCity;
import com.jinanlongen.manatee.domain.SnItemparameters;
import com.jinanlongen.manatee.domain.SnNation;
import com.jinanlongen.manatee.domain.SnParOption;
import com.jinanlongen.manatee.domain.SnParOptioncode;
import com.jinanlongen.manatee.domain.SnSecondCategory;
import com.jinanlongen.manatee.domain.SnShopCategory;
import com.jinanlongen.manatee.repository.CategoryItemparametersRep;
import com.jinanlongen.manatee.repository.ParOptioncodeRep;
import com.jinanlongen.manatee.repository.SnBrandRep;
import com.jinanlongen.manatee.repository.SnCategoryRep;
import com.jinanlongen.manatee.repository.SnCityRep;
import com.jinanlongen.manatee.repository.SnItemparametersRep;
import com.jinanlongen.manatee.repository.SnNationRep;
import com.jinanlongen.manatee.repository.SnParOptionRep;
import com.jinanlongen.manatee.repository.SnSecondCategoryRep;
import com.jinanlongen.manatee.repository.SnShopCategoryRep;
import com.jinanlongen.manatee.utils.SnUtils;
import com.suning.api.entity.custom.NewbrandQueryRequest;
import com.suning.api.entity.custom.NewbrandQueryResponse.QueryNewbrand;
import com.suning.api.entity.item.CategoryQueryRequest;
import com.suning.api.entity.item.CategoryQueryResponse.CategoryQuery;
import com.suning.api.entity.item.ItemparametersQueryResponse.ItemparametersQuery;
import com.suning.api.entity.item.ItemparametersQueryResponse.ParOption;
import com.suning.api.entity.master.CityQueryResponse.City;
import com.suning.api.entity.master.NationQueryResponse.Nation;
import com.suning.api.entity.shop.ShopcategoryQueryResponse.SecondCategory;
import com.suning.api.entity.shop.ShopcategoryQueryResponse.ShopCategory;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月30日
 */
@Service
public class SnService {
	@Autowired
	SnShopCategoryRep shopCategoryRep;
	@Autowired
	SnSecondCategoryRep secondCategoryRep;
	@Autowired
	SnCategoryRep lcategoryQueryRep;
	@Autowired
	SnBrandRep lqueryNewbrandRep;
	@Autowired
	SnItemparametersRep litemparametersQueryRep;
	@Autowired
	CategoryItemparametersRep categoryItemparametersRep;
	@Autowired
	SnParOptionRep lparOptionRep;
	@Autowired
	SnNationRep snNationRep;
	@Autowired
	SnCityRep snCityRep;
	@Autowired
	ParOptioncodeRep parOptioncodeRep;
	private static Logger logger = LoggerFactory.getLogger(SnService.class);

	// 根据CategoryCode获取模板信息
	// @Transactional(rollbackFor = { Exception.class })
	public void itemParameters() {
		List<String> list = lcategoryQueryRep.findAll().stream().map(i -> i.getCategoryCode())
				.collect(Collectors.toList());
		logger.info("共有{}个类目", list.size());
		SnUtils Sn = new SnUtils();
		List<ItemparametersQuery> ilist;
		SnItemparameters lt;
		SnCategoryItemparameters ca;
		SnItemparameters l;
		SnParOptioncode parOptioncode;
		List<ParOption> palist;
		SnParOption lp;
		for (String string : list) {// 循环所有类目
			ilist = Sn.itemparametersQuery(string);
			logger.info("{}类目下，共{}个属性", string, ilist.size());
			for (ItemparametersQuery itemparametersQuery : ilist) {// 循环类目下的par
				lt = litemparametersQueryRep.findByParCode(itemparametersQuery.getParCode());// par exist?
				ca = new SnCategoryItemparameters();// 记录类目 par 对应关系
				if ("3".equals(itemparametersQuery.getParType())) {// 手动输入的 无paroption
					if (lt != null) {
						ca.setCategoryCode(string);
						ca.setItemParametersId(lt.getId());
						ca.setParaTemplateCode(itemparametersQuery.getParaTemplateCode());
						categoryItemparametersRep.save(ca);// 保存类目。par对应关系
					} else {
						l = savePar(itemparametersQuery);// 保存par
						ca.setCategoryCode(string);
						ca.setItemParametersId(l.getId());
						ca.setParaTemplateCode(itemparametersQuery.getParaTemplateCode());
						categoryItemparametersRep.save(ca);// 保存类目。par对应关系
					}
				} else if ("1".equals(itemparametersQuery.getParType())
						|| "2".equals(itemparametersQuery.getParType())) {// 存在选项的
					if (lt != null) {
						ca.setCategoryCode(string);
						ca.setItemParametersId(lt.getId());
						ca.setParaTemplateCode(itemparametersQuery.getParaTemplateCode());
						categoryItemparametersRep.save(ca);// 保存类目。par对应关系

						palist = itemparametersQuery.getParOption();
						logger.info("{}类目下{}，共{}个option", string, itemparametersQuery.getParaTemplateDesc(),
								palist.size());
						for (ParOption parOption : palist) {
							SnParOption fl = lparOptionRep.findByParOptionDescAndParCode(parOption.getParOptionCode(),
									itemparametersQuery.getParCode());// option 是否存在
							if (null != fl) {
								parOptioncode = new SnParOptioncode();
								parOptioncode.setParaTemplateCode(itemparametersQuery.getParaTemplateCode());
								parOptioncode.setParOptionId(fl.getId());
								parOptioncodeRep.save(parOptioncode);// 保存类目。option对应关系

							} else {
								lp = new SnParOption();
								lp.setParOptionCode(parOption.getParOptionCode());
								lp.setParOptionDesc(parOption.getParOptionDesc());
								lp.setParCode(itemparametersQuery.getParCode());
								lp = lparOptionRep.save(lp);// 保存option?

								parOptioncode = new SnParOptioncode();
								parOptioncode.setParaTemplateCode(itemparametersQuery.getParaTemplateCode());
								parOptioncode.setParOptionId(lp.getId());
								parOptioncodeRep.save(parOptioncode);// 保存类目。option对应关系

							}

						}

					} else {

						l = savePar(itemparametersQuery);// 保存par
						palist = itemparametersQuery.getParOption();
						logger.info("{}类目下{}，共{}个option", string, itemparametersQuery.getParaTemplateDesc(),
								palist.size());
						for (ParOption parOption : palist) {// 循环option
							SnParOption fl = lparOptionRep.findByParOptionDescAndParCode(parOption.getParOptionCode(),
									itemparametersQuery.getParCode());
							if (null != fl) {
								parOptioncode = new SnParOptioncode();
								parOptioncode.setParaTemplateCode(itemparametersQuery.getParaTemplateCode());
								parOptioncode.setParOptionId(fl.getId());
								parOptioncodeRep.save(parOptioncode);

							} else {
								lp = new SnParOption();
								lp.setParOptionCode(parOption.getParOptionCode());
								lp.setParOptionDesc(parOption.getParOptionDesc());
								lp.setParCode(itemparametersQuery.getParCode());
								lp = lparOptionRep.save(lp);// ? can not return id

								parOptioncode = new SnParOptioncode();
								parOptioncode.setParaTemplateCode(itemparametersQuery.getParaTemplateCode());
								parOptioncode.setParOptionId(lp.getId());
								parOptioncodeRep.save(parOptioncode);

							}

						}

						ca.setCategoryCode(string);
						ca.setItemParametersId(l.getId());
						ca.setParaTemplateCode(itemparametersQuery.getParaTemplateCode());
						categoryItemparametersRep.save(ca);
					}
				} else {
					logger.info("par类型{}", itemparametersQuery.getParType());
				}
			}
			logger.info("{}类目，同步完成", string);
		}

	}

	private SnItemparameters savePar(ItemparametersQuery itemparametersQuery) {
		SnItemparameters l = new SnItemparameters();
		l.setDataType(itemparametersQuery.getDataType());
		l.setIsMust(itemparametersQuery.getIsMust());
		l.setParaTemplateDesc(itemparametersQuery.getParaTemplateDesc());
		l.setParCode(itemparametersQuery.getParCode());
		l.setParName(itemparametersQuery.getParName());
		l.setParType(itemparametersQuery.getParType());
		l.setParUnit(itemparametersQuery.getParUnit());
		return litemparametersQueryRep.save(l);// 保存par
	}

	// 根据CategoryCode获取品牌信息
	public void brandQuery() {
		SnUtils Sn = new SnUtils();
		NewbrandQueryRequest request;
		List<QueryNewbrand> brandList;
		SnBrand lq;
		List<String> list = lcategoryQueryRep.findAll().stream().map(i -> i.getCategoryCode())
				.collect(Collectors.toList());
		for (String string : list) {
			request = new NewbrandQueryRequest();
			request.setCategoryCode(string);
			brandList = Sn.brandQuery(request);
			for (QueryNewbrand queryNewbrand : brandList) {
				int count = lqueryNewbrandRep.brandExist(queryNewbrand.getCategoryCode(), queryNewbrand.getBrandCode());
				if (count == 0) {

					lq = new SnBrand();
					lq.setBrandCode(queryNewbrand.getBrandCode());
					lq.setBrandName(queryNewbrand.getBrandName());
					lq.setCategoryCode(queryNewbrand.getCategoryCode());
					lqueryNewbrandRep.save(lq);
				} else {
					logger.info("品牌已存在，类目：{}，code：{}", queryNewbrand.getCategoryCode(), queryNewbrand.getBrandCode());
				}
			}
		}
		logger.info("获取品牌信息成功！");
	}

	// 获取苏宁授权的采购目录信息
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public void categoryQuery() {
		SnUtils Sn = new SnUtils();
		CategoryQueryRequest request = new CategoryQueryRequest();
		List<CategoryQuery> list = Sn.categoryQuery(request);
		for (CategoryQuery categoryQuery : list) {
			SnCategory ca = new SnCategory();
			ca.setCategoryCode(categoryQuery.getCategoryCode());
			ca.setCategoryName(categoryQuery.getCategoryName());
			ca.setDescPath(categoryQuery.getDescPath());
			ca.setGrade(categoryQuery.getGrade());
			ca.setIsBottom(categoryQuery.getIsBottom());
			lcategoryQueryRep.save(ca);
		}
		logger.info("同步苏宁授权的采购目录信息成功！");
	}

	// // 同步店铺分类
	// public void shopCategoryQuery() {
	// SnUtils Sn = new SnUtils();
	// List<ShopCategory> list = Sn.shopCategoryQuery(20, 1);
	// for (ShopCategory shopCategory : list) {
	// saveShopCategory(shopCategory);
	// }
	// }

	public void saveShopCategory(ShopCategory shopCategory) {

		SnShopCategory shop = new SnShopCategory();
		shop.setCategorySet(shopCategory.getCategorySet());
		shop.setFirstCategoryCode(shopCategory.getFirstCategoryCode());
		shop.setFirstCategoryImg(shopCategory.getFirstCategoryImg());
		shop.setFirstCategoryName(shopCategory.getFirstCategoryName());
		shop.setFirstCategorySort(shopCategory.getFirstCategorySort());
		List<SecondCategory> slist = shopCategory.getSecondCategory();

		List<SnSecondCategory> llist = new ArrayList<SnSecondCategory>();
		if (slist != null && slist.size() != 0) {
			for (SecondCategory secondCategory : slist) {
				SnSecondCategory se = new SnSecondCategory();
				se.setSecondCategoryCode(secondCategory.getSecondCategoryCode());
				se.setSecondCategoryImg(secondCategory.getSecondCategoryImg());
				se.setSecondCategoryName(secondCategory.getSecondCategoryName());
				se.setSecondCategorySort(secondCategory.getSecondCategorySort());
				secondCategoryRep.save(se);
				llist.add(se);
			}
		}

		shop.setSecondCategorys(llist);
		shopCategoryRep.save(shop);
	}

	public void nation(String nation) {
		SnUtils Sn = new SnUtils();
		List<Nation> list = Sn.nation(nation);
		SnNation sn;
		logger.info("开始同步{}国家信息,共有{}条记录", nation, list.size() + "");
		for (Nation nation2 : list) {

			if (!snNationRep.exists(nation2.getNationCode())) {
				sn = new SnNation();
				sn.setNationCode(nation2.getNationCode());
				sn.setNationName(nation2.getNationName());
				snNationRep.save(sn);
			}
		}
	}

	public void city(String nationCode) {
		SnUtils Sn = new SnUtils();
		List<City> list = Sn.city(nationCode);
		SnCity sn;
		logger.info("开始同步{}国家的城市信息,共有{}条记录", nationCode, list.size() + "");
		for (City city : list) {
			if (!snCityRep.exists(city.getCityCode())) {
				sn = new SnCity();
				sn.setCityCode(city.getCityCode());
				sn.setCityname(city.getCityName());
				sn.setDistrictCode(city.getDistrictCode());
				sn.setDistrictName(city.getDistrictName());
				sn.setNationCode(nationCode);
				sn.setProvinceCode(city.getProvinceCode());
				sn.setProvinceName(city.getProvinceName());
				sn.setRegionCode(city.getRegionCode());
				sn.setRegionName(city.getRegionName());
				snCityRep.save(sn);

			}
		}
	}

	public void printTest() {
		SnItemparameters l = litemparametersQueryRep.findByParCode("G99017");
		System.out.println(l.getParName());
	}
}
