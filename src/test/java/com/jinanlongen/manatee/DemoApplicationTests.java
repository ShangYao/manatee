package com.jinanlongen.manatee;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.jd.open.api.sdk.domain.Ware;
import com.jd.open.api.sdk.request.ware.WareWriteUpdateWareRequest;
import com.jinanlongen.manatee.domain.Shop;
import com.jinanlongen.manatee.domain.SnCategoryItemparameters;
import com.jinanlongen.manatee.domain.SnItemparameters;
import com.jinanlongen.manatee.repository.CategoryItemparametersRep;
import com.jinanlongen.manatee.repository.SnItemparametersRep;
import com.jinanlongen.manatee.service.SnService;
import com.jinanlongen.manatee.utils.JdUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	SnItemparametersRep snItemparametersRep;
	@Autowired
	CategoryItemparametersRep categoryItemparametersRep;
	@Autowired
	SnService snService;

	@Test
	public void snSyntest() {
		snService.itemParameters();
	}

	@Test
	public void snApitest() {
		List<SnCategoryItemparameters> clist = categoryItemparametersRep.findByCategoryCode("R6191003");
		clist.forEach(i -> {
			SnItemparameters s = snItemparametersRep.findOne(i.getItemParametersId());
			System.out.println(JSON.toJSONString(s));
		});

	}

	@Test
	public void jdApitest() {
		Shop shop = new Shop();
		shop.setAccessToken("9a016de4-ac53-4648-827b-af54023140f3");
		shop.setAppKey("DE38EDBBB1F9F4AC799CE3EC0A3D1E50");
		shop.setAppSecret("1b6925655d724721951f36b3a09239bd");
		JdUtils jd = new JdUtils(shop);
		WareWriteUpdateWareRequest request = new WareWriteUpdateWareRequest();
		Ware ware = new Ware();
		ware.setWareId(1966837703l);
		ware.setTitle("全球购哥伦比亚（Columbia） Big & Tall 男修身韩版夹克");
		ware.setTransportId(0l);
		ware.setMobileDesc(
				"<div style=\"width:640px;\"> <img src=\"https://img20.360buyimg.com/imgzone/jfs/t18685/248/309923021/118893/3833c5ab/5a6aeb0aNdc3ee35f.jpg\"> <img src=\"https://img20.360buyimg.com/imgzone/jfs/t15490/9/1999730083/43340/9e9e75e/5a6aeb13N705348f9.jpg\"> </div> <div> <br></div>  <div><img src=\"https://img20.360buyimg.com/imgzone/jfs/t19090/300/309062340/11017/85e3f249/5a6aeb18N98d07c25.jpg\">  <div><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t19060/142/1466484230/131072/fba88d89/5acdb28bN539add95.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t19648/53/1546860239/65536/218eadef/5acdb28cNaccd98d7.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18643/312/1448073168/131072/3017357e/5acdb289N538e37d3.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t19513/364/1539917972/65536/a3c90950/5acdb28cN7713d741.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t16648/334/1571103990/262144/205d20eb/5acdb28dN5c5d7fcf.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t19792/14/1540291040/131072/2087ae58/5acdb28dN340e69e7.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18439/35/1553569912/262144/20ff653a/5acdb28fN6f4739e4.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t17449/255/1600441450/32768/d41c6066/5acdb290Nd8687b09.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t17863/322/1524807623/32768/b07eca40/5acdb291N459ee1bf.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t16621/290/1513896659/32768/10414e46/5acdb291Nc060a307.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18856/311/1583543479/32768/579c770f/5acdb291N1fa2f95a.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18172/266/1510495202/32768/6f38214c/5acdb292N67373e4d.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18583/254/1505171490/65536/f4270683/5acdb292N893f3cb3.jpg> <br /></div> <div><img src=\"https://img20.360buyimg.com/imgzone/jfs/t19366/317/318882909/10773/bb95810f/5a6aeb1eNc0a43125.jpg\"> <div> <table width=\"640\" align=\"left\"> <tr><td style=\"width: 100px; background-color: gainsboro;\">商品品牌</td><td style=\"background-color: aliceblue;\">Columbia/哥伦比亚</td></tr> <tr><td style=\"background-color: gainsboro;\">商品名称</td><td style=\"background-color: aliceblue;\">Columbia Big & Tall Cascades Explorer? Full Zip Fleece</td></tr> <tr><td style=\"background-color: gainsboro;\">商品货号</td><td style=\"background-color: aliceblue;\">3ab55eb1</td></tr> <tr><td style=\"background-color: gainsboro;\">商品英文描述</td><td style=\"background-color: aliceblue;\"><ul><div><li>SKU: #<!-- -->8545765</li><li>Columbia Mens Size Chart</li><li><div>Whether youre facing Northwestern weather or just your typical seasons chill, layer up for basic warmth in the Cascades Explorer? Full Zip Fleece.</div></li></div><li><div>Modern Classic is an easy, lightly relaxed fit for comfortable range of motion.</div></li><li><div>Warm, finely knit microfleece is designed to resist pilling for cross-season wear.</div></li><li><div>Stand collar.</div></li></ul></td></tr> <tr><td style=\"background-color: gainsboro;\">商品中文描述</td><td style=\"background-color: aliceblue;\"><ul><div><li>SKU：#。<!-- -->8545765。</li><li>伦比亚男子尺寸图。</li><li><div>无论你是面对西北地区的天气还是典型的季节寒冷，要在梯级探险者中为基本的温暖做准备。?全羊毛。</div></li></div><li><div>现代经典是一个轻松，轻松适合舒适的运动范围。</div></li><li><div>温暖，细密编织的微羊毛设计，以防止毛起球，以跨季节的磨损。</div></li><li><div>站着领子。</div></li></ul></td></tr><tr><td colspan=\"2\">(中文描述为软件翻译，仅供参考，购买请以英文介绍为准。如有疑问请咨询客服)</td></tr> </table> <div> <div style=\"width:640px;\">  <p><img src=\"https://img20.360buyimg.com/imgzone/jfs/t18730/283/316405724/239808/a4993f30/5a6aeb23Nb0d6fd1f.jpg\">  <img src=\"https://img20.360buyimg.com/imgzone/jfs/t13099/71/2173721666/122808/d6166658/5a6aeb27N983861ed.jpg\">  <img src=\"https://img20.360buyimg.com/imgzone/jfs/t18877/330/329969828/171569/4549bbf3/5a6aeb2cNecc0837b.jpg\">  <img src=\"https://img20.360buyimg.com/imgzone/jfs/t14962/208/2036793418/84996/74451b9c/5a6aeb31Ndd1eec9b.jpg\">   <img src=\"https://img20.360buyimg.com/imgzone/jfs/t16534/91/1954401281/155645/6dddfb8b/5a6aeb36Na2ec3ea7.jpg\">   <img src=\"https://img20.360buyimg.com/imgzone/jfs/t15079/229/2029650932/122629/e254cce1/5a6aeb3aNa6ac832a.jpg\">    </p> <br> </p> </div></div></div></div> </div>");
		ware.setIntroduction(
				"<table width=\"990\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"text-align:center\">  <tr>   <td><img src=\"http://img10.360buyimg.com/imgzone/jfs/t2653/234/1561690957/177037/6f1e8330/5742d365Nd5f67549.jpg\" alt=\"\"></td>  </tr> <td>        </td> <tr>     <td>         <img src=\"http://img10.360buyimg.com/imgzone/jfs/t2662/178/1572484963/49837/1c7dea54/5742d366N52833ac2.jpg\" alt=\"\"></td>  </tr>  <tr>   <td>    <img src=\"http://img10.360buyimg.com/imgzone/jfs/t2635/282/1558146367/19327/73a7a87b/5742d364N94fe828b.jpg\"  alt=\"\"></td>  </tr>     <td>   <img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t19060/142/1466484230/131072/fba88d89/5acdb28bN539add95.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t19648/53/1546860239/65536/218eadef/5acdb28cNaccd98d7.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18643/312/1448073168/131072/3017357e/5acdb289N538e37d3.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t19513/364/1539917972/65536/a3c90950/5acdb28cN7713d741.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t16648/334/1571103990/262144/205d20eb/5acdb28dN5c5d7fcf.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t19792/14/1540291040/131072/2087ae58/5acdb28dN340e69e7.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18439/35/1553569912/262144/20ff653a/5acdb28fN6f4739e4.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t17449/255/1600441450/32768/d41c6066/5acdb290Nd8687b09.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t17863/322/1524807623/32768/b07eca40/5acdb291N459ee1bf.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t16621/290/1513896659/32768/10414e46/5acdb291Nc060a307.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18856/311/1583543479/32768/579c770f/5acdb291N1fa2f95a.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18172/266/1510495202/32768/6f38214c/5acdb292N67373e4d.jpg> <br /><img width=\"640\" height=\"640\" src=http://img10.360buyimg.com/imgzone/jfs/t18583/254/1505171490/65536/f4270683/5acdb292N893f3cb3.jpg> <br />    </td>  <tr>   <td>    <img src=\"http://img10.360buyimg.com/imgzone/jfs/t2656/253/1591562488/19604/7567674e/5742d364N9e7a3ee5.jpg\"  alt=\"\"></td>  </tr>  <tr>   <td>    <div style=\"margin:0 auto; width:740px\">       <table width=\"740\" style=\"text-align:left;\" align=\"center\">    <tbody>     <tr>     <td style=\"background-color:Gainsboro;  width: 120px; \" ><dib style=\"width:120px;\">商品品牌</td><td style=\"background-color:AliceBlue; width:620px \">Columbia/哥伦比亚</td>     </tr>     <tr>     <td style=\"background-color:Gainsboro;\">商品名称</td><td style=\"background-color:AliceBlue;\">Columbia Big & Tall Cascades Explorer? Full Zip Fleece</td>     </tr>     <tr>     <td style=\"background-color:Gainsboro; \" >商品货号</td><td style=\"background-color:AliceBlue;\">3ab55eb1</td>     </tr>     <tr>     <td style=\"background-color:Gainsboro; \" >商品英文描述</td><td style=\"background-color:AliceBlue;\"> <ul><div><li>SKU: #<!-- -->8545765</li><li>Columbia Mens Size Chart</li><li><div>Whether youre facing Northwestern weather or just your typical seasons chill, layer up for basic warmth in the Cascades Explorer? Full Zip Fleece.</div></li></div><li><div>Modern Classic is an easy, lightly relaxed fit for comfortable range of motion.</div></li><li><div>Warm, finely knit microfleece is designed to resist pilling for cross-season wear.</div></li><li><div>Stand collar.</div></li></ul></td>     </tr>     <tr>     <td style=\"background-color:Gainsboro; \" >商品中文描述</td><td style=\"background-color:AliceBlue; \"><ul><div><li>SKU：#。<!-- -->8545765。</li><li>伦比亚男子尺寸图。</li><li><div>无论你是面对西北地区的天气还是典型的季节寒冷，要在梯级探险者中为基本的温暖做准备。?全羊毛。</div></li></div><li><div>现代经典是一个轻松，轻松适合舒适的运动范围。</div></li><li><div>温暖，细密编织的微羊毛设计，以防止毛起球，以跨季节的磨损。</div></li><li><div>站着领子。</div></li></ul></td>     </tr>     <tr>     <td colspan=\"2\" >(中文描述为软件翻译，仅供参考，购买请以英文介绍为准。如有疑问请咨询客服)</td>     </tr>    </tbody>   </table> </div> </td>   </tr>  <tr>    <td></td></tr>   <tr>    <td></td></tr>   <td> <img src=\"http://img10.360buyimg.com/imgzone/jfs/t2860/35/1809719661/296479/8ab1a302/574bdca2N560f3c5c.jpg\" alt=\"\"></td>  </tr>     <tr>   <td>    <img src=\"http://img10.360buyimg.com/imgzone/jfs/t2758/138/1768186426/160932/2eb8bb28/574bda58N86995d4b.jpg\" alt=\"\"></td>  </tr>    <tr>   <td>    <img src=\"http://img10.360buyimg.com/imgzone/jfs/t2872/256/1575031493/167794/45254d56/5742d366N73b5cf45.jpg\" alt=\"\"></td>  </tr>  <tr>   <td>    <img src=\"http://img10.360buyimg.com/imgzone/jfs/t2908/250/1579520090/135290/1abec888/5742d365N0874e90a.jpg\"  alt=\"\"></td>  </tr>  <tr>   <td>    <img src=\"http://img10.360buyimg.com/imgzone/jfs/t2908/246/1548133564/120777/6adbb0d5/5742d366N37506837.jpg\" alt=\"\"></td>  </tr>  <tr>   <td>    <img src=\"http://img10.360buyimg.com/imgzone/jfs/t2737/240/1594865450/125502/5464c3e7/5742d366Nfa7c51b9.jpg\" alt=\"\"></td>  </table>");
		request.setWare(ware);
		jd.updateWare(request);
	}

}
