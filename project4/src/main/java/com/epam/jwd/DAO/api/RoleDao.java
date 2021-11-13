package com.epam.jwd.DAO.api;

import com.epam.jwd.DAO.model.user.Role;

public interface RoleDao {
    int getIdByRoleName(String roleName);
    Role getRoleById(int role_id);
}
