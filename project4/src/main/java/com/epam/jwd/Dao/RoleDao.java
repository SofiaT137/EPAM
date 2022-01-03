package com.epam.jwd.Dao;

import com.epam.jwd.Dao.model.user.Role;

public interface RoleDao {
    int getIdByRoleName(String roleName);
    Role getRoleById(int roleId);
}
