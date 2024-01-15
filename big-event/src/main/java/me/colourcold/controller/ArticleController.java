package me.colourcold.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import me.colourcold.pojo.Article;
import me.colourcold.pojo.PageBean;
import me.colourcold.pojo.Result;
import me.colourcold.service.ArticleService;
import me.colourcold.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success("文章列表");
    }

    @GetMapping
    public Result<PageBean<Article>> get(Integer pageNum, Integer pageSize, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String state) {
        PageBean<Article> list = articleService.get(pageNum, pageSize, categoryId, state);
        return Result.success(list);
    }
}
