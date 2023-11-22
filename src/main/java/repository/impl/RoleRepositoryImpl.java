package repository.impl;

import entity.Role;
import repository.RoleRepository;
import utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RoleRepositoryImpl implements RoleRepository<Role> {
    private Map<Long,Role> roles = new HashMap<>();
    private static Long id = 0L;

    public RoleRepositoryImpl() {
        roles = (Map<Long, Role>) JsonUtil.getMapOfObjects("JsonRoleList.json",(Class<? extends Map<?, ?>>) roles.getClass(),Long.class,Role.class);
        id = (long) roles.size();
    }

    @Override
    public Optional<Role> create(Role role) {
        role.setId(++id);
        roles.put(id, role);
        return Optional.of(roles.get(id));
    }

    @Override
    public Optional<Role> update(Role role) {
        roles.put(role.getId(), role);
        return Optional.of(role);
    }

    @Override
    public Optional<Role> getById(Long id) {
        return Optional.ofNullable(roles.get(id));
    }

    @Override
    public boolean deleteById(Long id) {
        return roles.remove(id) != null;
    }

    @Override
    public Optional<Role> findByRole(String s) {
        return Optional.of(roles.values().stream().filter(r->r.getRole().equals(s)).findFirst().orElseThrow());
    }
}
