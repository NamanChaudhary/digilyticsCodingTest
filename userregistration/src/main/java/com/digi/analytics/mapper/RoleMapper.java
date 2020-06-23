package com.digi.analytics.mapper;

import com.digi.analytics.model.Role;
import com.digi.analytics.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class RoleMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int i) throws SQLException {
        Role role = new Role();
        role.setRolId(Integer.parseInt(rs.getString("rolId")));
        role.setRoleName(rs.getString("roleName"));
        return role;
    }
}
