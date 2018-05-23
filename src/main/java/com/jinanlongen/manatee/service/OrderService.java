package com.jinanlongen.manatee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年4月11日
 */
@Service
public class OrderService {
  @Autowired
  private JdService jd;
  private static Logger logger = LoggerFactory.getLogger(OrderService.class);

  public void excuteOrder(String[] order) {
    if ("sn".equals(order[0])) {
      logger.info("执行苏宁命令");
    } else {
      logger.info("执行京东命令");
      excuteJdOrder(order);
    }
  }

  public void excuteJdOrder(String[] order) {
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
    // printMessage(jd.synAllbrand());
    // printMessage(jd.synAllCategory());
    printMessage(jd.synAllcategoryAttr());
    printMessage(jd.synAllAtrribute());
    printMessage(jd.synColorAndSize());

  }

  public void printMessage(String message) {
    System.out.println(message);
  }

}
