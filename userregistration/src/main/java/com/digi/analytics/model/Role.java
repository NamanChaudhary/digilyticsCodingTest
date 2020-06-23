package com.digi.analytics.model;

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

import com.digi.analytics.postgress.dao.UserDaoImpl;

import java.util.concurrent.atomic.AtomicInteger;

public class Role {

  private int rolId;
  private String roleName;

  public Role(AtomicInteger roleId, Role role) {
    this.rolId = roleId.getAndIncrement();

  }

  public Role() {

  }

  public int getRolId() {
    return rolId;
  }

  public void setRolId(int rolId) {
    this.rolId = rolId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

}
