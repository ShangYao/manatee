package com.jinanlongen.manatee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jinanlongen.manatee.service.JdService;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2017年11月27日
 */
@Component
public class OrderRun implements CommandLineRunner {
	@Autowired
	private JdService jd;

	@Override
	public void run(String... arg0) throws Exception {

		printMessage("检查输入指令----");
		// jd.synOneShop("31");
		// jd.updateByDutyId(37777);
		// printMessage(jd.updateCategoryAttr(Long.valueOf("1000012771")));
		// printMessage(jd.updateCategory(Long.valueOf("9737"), "11"));
		if (arg0 == null || arg0.length == 0) {
			printMessage("无同步指令输入");
		} else if (arg0.length == 1 || arg0.length == 2) {
			excuteOrder(arg0[0]);
		} else {
			printMessage("指令输入格式错误");
		}

	}

	public void excuteOrder(String orders) {
		String[] order = orders.split(",");
		printMessage("指令执行中--");
		if ("synAll".equals(order[0])) {
			synAll();
		} else if ("synBrand".equals(order[0])) {
			printMessage(jd.synAllbrand());
		} else if ("synByDuty".equals(order[0])) {
			if (order.length == 2) {
				jd.updateByDutyId(Integer.valueOf(order[1]));
			} else {
				printMessage("无ID输入");
			}
		} else if ("synCategory".equals(order[0])) {
			if (order.length == 2) {
				printMessage(jd.updateCategory(Long.valueOf(order[1])));
			} else {
				printMessage("无ID输入");
			}
		} else if ("synCategoryAttr".equals(order[0])) {
			if (order.length == 2) {
				printMessage(jd.updateCategoryAttr(Long.valueOf(order[1])));
			} else {
				printMessage("无ID输入");
			}
		} else if ("synAttrValue".equals(order[0])) {
			if (order.length == 3) {
				if ("ColorOrSize".equals(order[2])) {
					printMessage(jd.updateColorAndSizeValue(Long.valueOf(order[1])));
				} else {
					printMessage(jd.updateAttrValue(Long.valueOf(order[1]), null));
				}
			} else if (order.length == 4) {
				printMessage(jd.updateAttrValue(Long.valueOf(order[1]), order[3]));
			} else {
				printMessage("无ID输入");
			}
		} else {
			printMessage("指令有误");
		}
	}

	public void synAll() {
		printMessage(jd.synAllbrand());
		printMessage(jd.synAllCategory());
		printMessage(jd.synAllcategoryAttr());
		printMessage(jd.synAllAtrribute());
		printMessage(jd.synColorAndSize());

	}

	public void printMessage(String message) {
		System.out.println(message);
	}

}
