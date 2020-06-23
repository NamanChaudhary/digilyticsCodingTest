package com.digi.analytics.postgress.dao;

        /*
        Copyright ${today.year}-present Open Networking Foundation

        Licensed under the Apache License,Version2.0(the"License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing,software
        distributed under the License is distributed on an"AS IS"BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
        */

import com.digi.analytics.mapper.UserMapper;
import com.digi.analytics.model.Role;
import com.digi.analytics.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    public UserDaoImpl(JdbcTemplate template) {
        this.template = template;
    }
    static JdbcTemplate template;
    AtomicInteger rolId = new AtomicInteger(0);

    @Override
    public List<User> findAll() {
        return template.query("select u.userId, u.name, r.roleName, u.EmailId from user u, role r where u.roleId==r.roleId ", new UserMapper());
    }

    @Override
    public void insertUser(User user) {

        if(rolId.get()==0){
         rolId.set(getMaxRolId("Role")+1);
        }


        final String sql = "insert into user(userId, name, rolId ,emailId) values(:userId,:rolId,:name,:emailId)";
        KeyHolder holder = new GeneratedKeyHolder();
        user.getRole().setRolId(rolId.getAndIncrement());
        insertRole(user.getRole());
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("name", user.getName())
                .addValue("rolId", user.getRole().getRolId())
                .addValue("emailId", user.getEmailId());
        template.update(sql,param, holder);

    }

    @Override
    public void insertRole(Role role) {

        final String sql = "insert into Role(roleId, roleName) values(:roleId,:roleName)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()

                .addValue("roleId", role.getRolId())
                .addValue("roleName", role.getRoleName());
        template.update(sql,param, holder);

    }

    public static int getMaxRolId(String tableName){

        return template.queryForObject("SELECT MAX(ROLEID FROM ROLE", Integer.class);
    }
}
