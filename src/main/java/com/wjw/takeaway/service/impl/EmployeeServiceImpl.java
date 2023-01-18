package com.wjw.takeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjw.takeaway.entity.Employee;
import com.wjw.takeaway.mapper.EmployeeMapper;
import com.wjw.takeaway.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * Description: 业务处理层实现类
 * mp规范，继承父类，实现接口；
 * 注意泛型；
 *
 * @author wjw
 * @date 2022年12月23日 15:21
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
