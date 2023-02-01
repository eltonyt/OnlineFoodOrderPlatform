package com.elton.foodorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elton.foodorder.entity.Employee;
import com.elton.foodorder.service.EmployeeService;
import com.elton.foodorder.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

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

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("employee");
        return R.success("Logged Out");
    }

    @PostMapping
    public R<String> createEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        String userName = employee.getUsername();
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, userName);
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return R.success("Success");
    }

    @GetMapping("/page")
    public R<Page<Employee>> getEmployee(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);
        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        if (name != null)
            queryWrapper.like(Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("Employee Updated");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee == null)
            return R.error("No Employee Found");
        return R.success(employee);
    }
}
