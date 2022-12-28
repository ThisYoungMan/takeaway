package com.wjw.takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wjw.takeaway.common.R;
import com.wjw.takeaway.entity.Employee;
import com.wjw.takeaway.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Description: 接口层
 *
 * @author wjw
 * @date 2022年12月23日 15:24
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request Http请求
     * @param employee 员工对象
     * @return R
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        log.info("1.将登陆密码进行 md5 加密处理，使用 DigestUtils 工具类，加密后 password={}", password);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 封装条件
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        log.info("2.根据用户名查询数据库，username={}", employee.getUsername());

        log.info("3.判断用户是否存在");
        if (emp == null) {
            log.error("3.1 登录失败，用户不存在");
            return R.error("登录失败，用户不存在");
        }

        log.info("4.判断密码md5处理后，是否与数据库密码一致");
        if (!emp.getPassword().equals(password)) {
            log.error("4.1 登录失败，密码错误");
            return R.error("登录失败，密码错误");
        }

        log.info("5.查看用户状态，0-禁用，1-正常");
        if (emp.getStatus() == 0) {
            log.error("5.1 登录失败，账户被禁用，该用户为 status={}", emp.getStatus());
            return R.error("登录失败，账户被禁用");
        }

        log.info("6.登录成功，将用户ID存入Session，并返回登陆成功");
        request.getSession().setAttribute("emp_id", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出登录
     *
     * @param request Http请求
     * @return R
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("emp_id");
        return R.success("退出成功");
    }
}
