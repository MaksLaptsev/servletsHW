package service.impl;

import entity.Role;
import exception.RoleException;
import repository.RoleRepository;
import repository.impl.RoleRepositoryImpl;
import service.RoleService;

public class RoleServiceImpl implements RoleService<Role> {
    private final RoleRepository<Role> roleRepository;

    public RoleServiceImpl() {
        roleRepository = new RoleRepositoryImpl();
    }

    @Override
    public Role create(Role role) {
        return roleRepository.create(role).orElseThrow(()-> new RoleException("Can not to create Role"));
    }

    @Override
    public Role update(Role role) {
        Role roleUpd = roleRepository.getById(role.getId()).orElseThrow(()-> new RoleException("Fail to find Role with id: "+role.getId()));
        roleUpd.setRole(role.getRole());
        return roleRepository.update(roleUpd).orElseThrow(()-> new RoleException("Can not to update Role"));
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.getById(id).orElseThrow(()-> new RoleException("Fail to find Role with id: "+id));
    }

    @Override
    public boolean deleteById(Long id) {
        return roleRepository.deleteById(id);
    }

    @Override
    public Role findByRole(String s) {
        return roleRepository.findByRole(s).orElseThrow(()->new RoleException("Fail to find Role with name: "+s));
    }
}
