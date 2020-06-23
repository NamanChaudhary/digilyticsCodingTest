package com.digi.analytics.mapper;

import com.digi.analytics.model.Role;
import com.digi.analytics.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;



public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setUserId(rs.getString("userId"));
        user.setEmailId(rs.getString("emailId"));
        user.setName(rs.getString("name"));
        Role role = new Role();
        role.setRolId(rs.getInt("roleId"));
        role.setRoleName(rs.getString("roleName"));
        user.setRole(role);
        return user;
    }
}
