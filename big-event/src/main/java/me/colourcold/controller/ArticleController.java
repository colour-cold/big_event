package me.colourcold.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import me.colourcold.pojo.Result;
import me.colourcold.utils.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    public Result lists(@RequestHeader(name = "Authorization") String token, HttpServletResponse response) {
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return Result.success("文章列表");
        }catch (Exception e) {
            response.setStatus(401);
            return Result.error("未登录");
        }
    }
}