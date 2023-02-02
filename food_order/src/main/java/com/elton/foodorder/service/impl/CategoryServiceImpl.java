package com.elton.foodorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elton.foodorder.entity.Category;
import com.elton.foodorder.mapper.CategoryMapper;
import com.elton.foodorder.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
