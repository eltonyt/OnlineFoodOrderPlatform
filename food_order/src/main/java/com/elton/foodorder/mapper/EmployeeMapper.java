package com.elton.foodorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elton.foodorder.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
