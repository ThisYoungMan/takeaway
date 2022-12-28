package com.wjw.takeaway.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Description: 全局异常处理
 *
 * @author wjw
 * @date 2022年12月28日 19:03
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 统一处理 SQLIntegrityConstraintViolationException 异常
     *
     * @param ex
     * @return R.error(" 账号重复 ， 添加失败 ")
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error("异常信息={}", ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            String msg = "账户：" + split[2] + " 已存在，添加失败";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
}
