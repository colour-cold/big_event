package me.colourcold.service;

import me.colourcold.mapper.UserMapper;
import me.colourcold.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {

    User findByUsername(String username);

    void register(String username, String password);
}
