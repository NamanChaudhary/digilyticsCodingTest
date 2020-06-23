package com.digi.analytics.postgress.dao;

import com.digi.analytics.model.Role;
import com.digi.analytics.model.User;

import java.util.List;

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

public interface UserDao {

    void insertUser(User user);

     List<User> findAll();

     void insertRole(Role role);
}
