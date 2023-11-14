package repository.impl;

import entity.Role;
import repository.RoleRepository;
import utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class RoleRepositoryImpl implements RoleRepository<Role> {
    private Map<Long,Role> roles = new HashMap<>();
    private static Long id = 0L;

    public RoleRepositoryImpl() {
        roles = (Map<Long, Role>) JsonUtil.getMapOfObjects("JsonRoleList.json",(Class<? extends Map<?, ?>>) roles.getClass(),Long.class,Role.class);
        id = (long) roles.size();
    }

    @Override
    public Role create(Role role) {
        role.setId(++id);
        roles.put(id, role);
        return roles.get(id);
    }

    @Override
    public Role update(Role role) {
        roles.put(role.getId(), role);
        return role;
    }

    @Override
    public Role getById(Long id) {
        return roles.get(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return roles.remove(id) != null;
    }

    @Override
    public Role findByRole(String s) {
        return roles.values().stream().filter(r->r.getRole().equals(s)).findFirst().orElseThrow();
    }
}
