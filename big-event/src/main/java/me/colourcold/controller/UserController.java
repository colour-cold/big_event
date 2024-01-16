package me.colourcold.controller;


import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import me.colourcold.pojo.Result;
import me.colourcold.pojo.User;
import me.colourcold.service.UserService;
import me.colourcold.utils.JwtUtil;
import me.colourcold.utils.Md5Util;
import me.colourcold.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User user = userService.findByUsername(username);
        if (user == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByUsername(username);
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        if (loginUser.getPassword().equals(Md5Util.getMD5String(password))) {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
            op.set("token", token, 12, TimeUnit.HOURS);
            return Result.success(token);
        } else {
            return Result.error("密码错误");
        }
    }

    @GetMapping("/userInfo")
    public Result userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
//        Map<String, Object> userMap = JwtUtil.parseToken(token);
//        User user = userService.findByUsername((String) userMap.get("username"));
        Map<String, Object> userMap = ThreadLocalUtil.get();
        User user = userService.findByUsername((String) userMap.get("username"));
        return Result.success(user);

    }

    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updatePic(@RequestParam @URL String url) {
        userService.avatarUrl(url);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> userMap) {
        String oldPwd = userMap.get("old_pwd");
        String newPwd = userMap.get("new_pwd");
        String rePwd = userMap.get("re_pwd");
        if (!StringUtils.isNotEmpty(oldPwd) || !StringUtils.isNotEmpty(newPwd) || !StringUtils.isNotEmpty(rePwd)) {
            return Result.error("缺少必要参数");
        }
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次密码输入不一致");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        if (!Md5Util.checkPassword(oldPwd, user.getPassword())) {
            return Result.error("当前密码错误");
        }
        if (Md5Util.getMD5String(newPwd).equals(user.getPassword())) {
            return Result.error("新密码不能与当前密码一致");
        }
        userService.updatePwd(userMap);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", username);
        String token = JwtUtil.genToken(claims);
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
        op.getAndDelete("token");
        op.set("token", token, 12, TimeUnit.HOURS);
        return Result.success(token);
    }
}
