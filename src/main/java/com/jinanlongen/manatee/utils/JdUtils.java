package com.jinanlongen.manatee.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.category.Category;
import com.jd.open.api.sdk.domain.list.CategoryAttrReadService.CategoryAttr;
import com.jd.open.api.sdk.domain.list.CategoryAttrValueReadService.CategoryAttrValueJos;
import com.jd.open.api.sdk.request.category.CategorySearchRequest;
import com.jd.open.api.sdk.request.list.CategoryReadFindAttrsByCategoryIdRequest;
import com.jd.open.api.sdk.request.list.CategoryReadFindValuesByAttrIdJosRequest;
import com.jd.open.api.sdk.request.list.CategoryReadFindValuesByIdJosRequest;
import com.jd.open.api.sdk.request.list.PopVenderCenerVenderBrandQueryRequest;
import com.jd.open.api.sdk.response.category.CategorySearchResponse;
import com.jd.open.api.sdk.response.list.CategoryReadFindAttrsByCategoryIdResponse;
import com.jd.open.api.sdk.response.list.CategoryReadFindValuesByAttrIdJosResponse;
import com.jd.open.api.sdk.response.list.CategoryReadFindValuesByIdJosResponse;
import com.jd.open.api.sdk.response.list.PopVenderCenerVenderBrandQueryResponse;
import com.jd.open.api.sdk.response.list.VenderBrandPubInfo;
import com.jinanlongen.manatee.domain.Shop;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月22日
 */

public class JdUtils {
	public static final String SERVER_URL = "https://api.jd.com/routerjson";

	public JdUtils(Shop shop) {
		this.accessToken = shop.getAccessToken();
		this.appKey = shop.getAppKey();
		this.appSecret = shop.getAppSecret();

	}

	private String accessToken;
	private String appKey;
	private String appSecret;

	// 查询商家已授权的品牌
	public List<VenderBrandPubInfo> queryBrand() {
		JdClient client = new DefaultJdClient(SERVER_URL, accessToken, appKey, appSecret);
		PopVenderCenerVenderBrandQueryRequest request = new PopVenderCenerVenderBrandQueryRequest();
		PopVenderCenerVenderBrandQueryResponse response = null;
		try {
			response = client.execute(request);
			// System.out.println(response.getMsg());

		} catch (JdException e) {
			e.printStackTrace();
		}
		return response.getBrandList();
	}

	// 获取商家类目信息
	public List<Category> get() {
		JdClient client = new DefaultJdClient(SERVER_URL, accessToken, appKey, appSecret);
		CategorySearchRequest request = new CategorySearchRequest();
		CategorySearchResponse response = null;
		try {
			// System.out.println(client);
			response = client.execute(request);
			// System.out.println(response.getMsg());
		} catch (JdException e) {
			e.printStackTrace();
		}
		return response.getCategory();

	}

	// 获取类目属性列表
	public List<CategoryAttr> findAttrsByCategoryId(Long id) {
		JdClient client = new DefaultJdClient(SERVER_URL, accessToken, appKey, appSecret);
		CategoryReadFindAttrsByCategoryIdRequest request = new CategoryReadFindAttrsByCategoryIdRequest();
		request.setCid(id);
		request.setField("categoryAttrs");
		CategoryReadFindAttrsByCategoryIdResponse response = null;
		try {
			response = client.execute(request);
			// System.out.println(response.getMsg());
		} catch (JdException e) {
			e.printStackTrace();
		}
		if (response != null) {
			return response.getCategoryAttrs();
		} else {
			System.out.println(
					"无返回值的类目ID：" + id + "---" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return null;
		}
	}

	// 获取类目属性值liebiao
	public List<CategoryAttrValueJos> findValuesByAttrIdJos(Long id) {
		JdClient client = new DefaultJdClient(SERVER_URL, accessToken, appKey, appSecret);
		CategoryReadFindValuesByAttrIdJosRequest request = new CategoryReadFindValuesByAttrIdJosRequest();
		CategoryReadFindValuesByAttrIdJosResponse response = null;
		request.setCategoryAttrId(id);
		try {
			// System.out.println("id=" + id);
			response = client.execute(request);
			// System.out.println(response.getMsg());
		} catch (JdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response != null) {
			return response.getCategoryAttrValues();
		} else {
			System.out.println(
					"无返回值的类目属性ID：" + id + "---" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return null;
		}
	}

	// 获取一个属性值
	public CategoryAttrValueJos findValuesByIdJos(Long id) {
		JdClient client = new DefaultJdClient(SERVER_URL, accessToken, appKey, appSecret);
		CategoryReadFindValuesByIdJosRequest request = new CategoryReadFindValuesByIdJosRequest();
		CategoryReadFindValuesByIdJosResponse response = null;
		request.setId(id);
		try {
			response = client.execute(request);
		} catch (JdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response != null) {
			return response.getCategoryAttrValue();
		} else {
			System.out.println(
					"无返回值的属性值ID：" + id + "---" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return null;
		}
	}
}
