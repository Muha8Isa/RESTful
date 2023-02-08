package se.lexicon.restful.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.restful.exception.DataDuplicateException;
import se.lexicon.restful.exception.DataNotFoundException;
import se.lexicon.restful.model.entity.User;
import se.lexicon.restful.model.entity.dto.RoleDto;
import se.lexicon.restful.model.entity.dto.UserDto;
import se.lexicon.restful.repository.RoleRepository;
import se.lexicon.restful.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    RoleRepository roleRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class}) //If an exception was thrown, rollback all changes in Database.
    public UserDto register(UserDto dto) {
        // Step 1: Check the methods' params
        if(dto == null) throw new IllegalArgumentException("UserDto data was null");
        if(dto.getUsername() == null || dto.getPassword() == null) throw new IllegalArgumentException("username or password data was null");
        if(dto.getRoles() == null || dto.getRoles().size() == 0) throw new IllegalArgumentException("roles data was null");

        // Step 2: Check the roles data
        for (RoleDto element: dto.getRoles())
            roleRepository.findById(element.getId()).orElseThrow(()-> new DataNotFoundException("role id was not valid"));

        // Step 3: Check the username that should not be duplicated.
        boolean  isExist = userRepository.existsByUsername(dto.getUsername());
        if(isExist) throw new DataDuplicateException("duplicate username error");

        // Step 4: convert the dto to entity
        User convertedToEntity = modelMapper.map(dto, User.class);

        // Step 5: Execute the save method of UserRepository
        User createdEntity = userRepository.save(convertedToEntity);

        // Step 6: Convert the created entity to dto.
        UserDto convertedToDto = modelMapper.map(createdEntity, UserDto.class);

        return convertedToDto;
    }

    @Override //ReadOnly method, so no need to use transactional.
    public Map<String, Object> findByUsername(String username) {
        if(username == null) throw new IllegalArgumentException("Username was null");
        User user = userRepository.findByUsername(username).orElseThrow(()->new DataNotFoundException("Username was not found error"));
        Map<String, Object> map = new HashMap<>();
        map.put("username",user.getUsername());
        map.put("roles", user.getRoles());
        map.put("expired", user.isExpired());
        return map;
    }

    @Override
    @Transactional
    public void disableUserByUsername(String username) {
        if(username == null) throw new IllegalArgumentException("Username was null");
        if(!userRepository.existsByUsername(username)) throw new DataNotFoundException("username was not valid");
        userRepository.updateExpiredByUsername(username, true);
    }
}
