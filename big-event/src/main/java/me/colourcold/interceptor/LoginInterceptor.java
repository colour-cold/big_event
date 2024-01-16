package me.colourcold.interceptor;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.colourcold.pojo.Result;
import me.colourcold.utils.JwtUtil;
import me.colourcold.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
            ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
            String tokenRedis = op.get("token");
            if (token.equals(tokenRedis)) {
                Map<String, Object> claims = JwtUtil.parseToken(token);
                ThreadLocalUtil.set(claims);
                return true;
            } else {
                System.out.println(token);
                System.out.println(tokenRedis);
                response.setStatus(404);
                return false;
            }
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}

