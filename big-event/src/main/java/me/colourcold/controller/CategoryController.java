package me.colourcold.controller;

import io.micrometer.common.util.StringUtils;
import me.colourcold.pojo.Category;
import me.colourcold.pojo.Result;
import me.colourcold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list() {
        return Result.success(categoryService.list());
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category c = categoryService.findById(id);
        return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        if (categoryService.findById(id) != null) {
            categoryService.delete(id);
            return Result.success();
        } else {
            return Result.error("id不存在");
        }

    }

}
