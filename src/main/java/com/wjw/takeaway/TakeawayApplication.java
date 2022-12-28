package com.wjw.takeaway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description: 项目启动类
 *
 * @author wjw
 * @date 2022年12月23日 14:16
 */
@Slf4j
@SpringBootApplication
public class TakeawayApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeawayApplication.class, args);
        log.info("❥(^_-)项目启动成功...");
    }
}
