package me.colourcold.service.impl;

import me.colourcold.mapper.UserMapper;
import me.colourcold.pojo.User;
import me.colourcold.service.UserService;
import me.colourcold.utils.Md5Util;
import me.colourcold.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username, md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void avatarUrl(String url) {
        Map<String, Object> userMap = ThreadLocalUtil.get();
        Integer id = (Integer) userMap.get("id");
        userMapper.avatraUrl(url, id);
    }

    @Override
    public void updatePwd(Map<String, String> userMap) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(userMap.get("new_pwd")), id);
    }
}
