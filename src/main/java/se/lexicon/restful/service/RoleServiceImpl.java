package se.lexicon.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.restful.model.entity.Role;
import se.lexicon.restful.model.entity.dto.RoleDto;
import se.lexicon.restful.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;


    @Override
    public List<RoleDto> getAll() {
        List<Role> roleList = roleRepository.findAllByOrderByIdDesc();
       return roleList.stream()
               .map(role -> new RoleDto(role.getId(), role.getName())) //This gets the Id and name from role entity and set it to RoleDto
               .collect(Collectors.toList());
    }

    @Override
    public RoleDto findById(Integer roleId) {
        if(roleId == null) throw new IllegalArgumentException("role id was null");
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isPresent()){
            Role entity = optionalRole.get();
            RoleDto dto = new RoleDto(entity.getId(), entity.getName());
            return dto;
        }
        return null;
    }

    @Override
    public RoleDto create(RoleDto roleDto) {
        return null;
    }

    @Override
    public void update(RoleDto roleDto) {

    }

    @Override
    public void delete(Integer roleId) {

    }
}
