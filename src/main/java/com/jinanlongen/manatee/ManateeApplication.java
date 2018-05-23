package com.jinanlongen.manatee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.jinanlongen.manatee.service.JdService;
import com.jinanlongen.manatee.service.OrderService;
import com.jinanlongen.manatee.service.SnService;

@SpringBootApplication
public class ManateeApplication implements CommandLineRunner {
  @Autowired
  private OrderService orderService;
  @Autowired
  SnService snService;
  @Autowired
  JdService jdservice;
  private static Logger logger = LoggerFactory.getLogger(ManateeApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ManateeApplication.class, args);

  }

  @Override
  public void run(String... arg0) throws Exception {
    // TODO Auto-generated method stub
    logger.info("输入指令:", arg0.toString());
    // snService.itemParameters();
    // orderService.synAll();
    jdservice.shoptest();
    // jd.synOneShop("31");
    // synAll();
    // jd.updateByDutyId(35934);
    if (arg0 == null || arg0.length == 0) {
      logger.info("无同步指令输入");
    } else if (arg0.length == 1 || arg0.length == 2) {
      orderService.excuteOrder(arg0[0].split(","));
    } else {
      logger.info("指令输入格式错误");
    }
  }
}
