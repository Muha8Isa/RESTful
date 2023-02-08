package se.lexicon.restful.service;

import se.lexicon.restful.model.entity.dto.UserDto;

import java.util.Map;

public interface UserService {
    UserDto register(UserDto dto);
    Map<String, Object> findByUsername(String username);

    void disableUserByUsername (String username);
}
