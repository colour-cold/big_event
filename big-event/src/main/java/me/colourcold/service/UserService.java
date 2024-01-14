package me.colourcold.service;

import me.colourcold.mapper.UserMapper;
import me.colourcold.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface UserService {

    User findByUsername(String username);

    void register(String username, String password);

    void update(User user);

    void avatarUrl(String url);

    void updatePwd(Map<String, String> userMap);
}
