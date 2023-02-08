package se.lexicon.restful.service;


import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.restful.exception.DataDuplicateException;
import se.lexicon.restful.exception.DataNotFoundException;
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
    @Autowired
    ModelMapper modelMapper; //used to convert to and from DTO. It has dependency inside pom file.


    @Override
    public List<RoleDto> getAll() {
        List<Role> roleList = roleRepository.findAllByOrderByIdDesc();
      /* return roleList.stream()
               .map(role -> new RoleDto(role.getId(), role.getName())) //This gets the Id and name from role entity and set it to RoleDto
               .collect(Collectors.toList()); */
        return modelMapper.map(roleList, new TypeToken<List<RoleDto>>(){}.getType()); //Converts from Role to RoleDTO using modelMapper.
    }

    @Override
    public RoleDto findById(Integer roleId) {
        if(roleId == null) throw new IllegalArgumentException("role id was null");
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isPresent()){
            Role entity = optionalRole.get();
          //  RoleDto dto = new RoleDto(entity.getId(), entity.getName());
           return modelMapper.map(entity, RoleDto.class); // Converts (from, to)
        }
        return null;
    }

    @Override
    public RoleDto create(RoleDto roleDto) {
        if(roleDto == null) throw new IllegalArgumentException("role data was null");
        if(roleDto.getId() != 0) throw new IllegalArgumentException("role id should be null or zero");
      //  Role convertedToEntity = new Role(roleDto.getName());
      //  Role createdEntity = roleRepository.save(convertedToEntity);
        Role createdEntity = roleRepository.save(modelMapper.map(roleDto, Role.class));
       // RoleDto convertedToDto = new RoleDto(createdEntity.getId(), createdEntity.getName());
        //return convertedToDto;
        return modelMapper.map(createdEntity, RoleDto.class);
    }

    @Override
    public void update(RoleDto roleDto) {
    if(roleDto == null) throw new IllegalArgumentException("role data was null");
    if(roleDto.getId() == 0) throw new IllegalArgumentException("role id should not be zero");

    if(!roleRepository.findById(roleDto.getId()).isPresent()) throw new DataNotFoundException("data not found Error");

    if(roleRepository.findByName(roleDto.getName()).isPresent()) throw new DataDuplicateException("duplicate Error");
   // Role convertedToEntity = new Role(roleDto.getId(), roleDto.getName());
   // roleRepository.save(convertedToEntity);
        roleRepository.save(modelMapper.map(roleDto, Role.class));
    }

    @Override
    public void delete(Integer roleId) {
    RoleDto roleDto = findById(roleId);
    if(roleDto == null) throw new DataNotFoundException("data not found");
    roleRepository.deleteById(roleId);
    }
}
