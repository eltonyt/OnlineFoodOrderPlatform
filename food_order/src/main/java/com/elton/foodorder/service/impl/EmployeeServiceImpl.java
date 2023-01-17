package com.elton.foodorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elton.foodorder.entity.Employee;
import com.elton.foodorder.mapper.EmployeeMapper;
import com.elton.foodorder.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
