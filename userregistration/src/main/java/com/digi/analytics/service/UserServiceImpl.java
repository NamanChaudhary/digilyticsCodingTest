package com.digi.analytics.service;

import com.digi.analytics.model.User;
import com.digi.analytics.postgress.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public Map<String,User> findAll() {
        List<User> users = userDao.findAll();
        Map<String,User> userMap = new HashMap<>();
        users.stream().filter(user->Objects.nonNull(user.getEmailId())).forEach(user->userMap.put(user.getEmailId(),user));
        return userMap;
    }

    @Override
    public void validate(List<User> users) {
        users.stream().forEach(this::verify);
    }

    private void verify(User user) {
        StringBuilder error = new StringBuilder();
        Map<String,User> existUser = findAll();
        if((Objects.isNull(user.getEmailId()))|| (user.getEmailId().split("@").length>2)){
            error.append("Invalid EmailId");
        }else if(Objects.nonNull(existUser.get(user.getEmailId()))){
            error.append("user already exit.") ;
        }
        user.setError(error.toString());
    }
}
