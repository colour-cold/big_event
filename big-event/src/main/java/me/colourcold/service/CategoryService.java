package me.colourcold.service;

import me.colourcold.pojo.Category;

import java.util.List;

public interface CategoryService {
    //新增分类
    void add(Category category);

    List<Category> list();

    //根据id查询分类信息
    Category findById(Integer id);

    //更新分类
    void update(Category category);

    void delete(Integer id);
}
