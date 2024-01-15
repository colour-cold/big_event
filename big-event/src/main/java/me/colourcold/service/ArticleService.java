package me.colourcold.service;

import me.colourcold.pojo.Article;
import me.colourcold.pojo.PageBean;

import java.util.List;

public interface ArticleService {

    void add(Article article);

    PageBean<Article> get(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
