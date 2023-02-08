package se.lexicon.restful.model.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserDto {
    private String username;
    private String password;
    private List<RoleDto> roles;
}
