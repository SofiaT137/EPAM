package com.epam.jwd.dao;

import com.epam.jwd.dao.model.user.Role;

public interface RoleDao {
    int getIdByRoleName(String roleName);
    Role getRoleById(int roleId);
}
