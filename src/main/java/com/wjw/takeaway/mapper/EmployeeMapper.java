package com.wjw.takeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjw.takeaway.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description: 持久化层
 *
 * @author wjw
 * @date 2022年12月23日 15:18
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
