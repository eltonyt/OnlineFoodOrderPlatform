package com.elton.foodorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.elton.foodorder.entity.Employee;
import com.elton.foodorder.service.EmployeeService;
import com.elton.foodorder.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    // REQUEST IS USED TO CREATE THE SESSION
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // MD5 ENCRYPT PASSWORD
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // SEARCH FOR USER
        String userName = employee.getUsername();
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, userName);
        Employee emp = employeeService.getOne(queryWrapper);

        // NO USERS WITH ENTERED USERNAME
        if (emp == null)
            return R.error("Credential not matched");

        // USER WITH ENTERED USERNAME BUT WRONG PASSWORD
        if (!emp.getPassword().equals(password))
            return R.error("Credential not matched");

        // USER WITH ENTERED USERNAME AND PASSWORD BUT NOT ACTIVE
        if (emp.getStatus() == 0)
            R.error("Your account is frozen/disabled");

        // MATCHED A ACTIVE EMPLOYEE, RETURN THIS EMPLOYEE AND ADD THIS EMPLOYEE TO THE SESSION
        HttpSession session = request.getSession();
        session.setAttribute("employee", emp.getId());
        return R.success(emp);
    }
}
