package com.epam.jwd.Dao.api;

import com.epam.jwd.Dao.model.user.Role;

public interface RoleDao {
    int getIdByRoleName(String roleName);
    Role getRoleById(int roleId);
}
