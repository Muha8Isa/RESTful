package se.lexicon.restful.model.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
public class UserDto {

    @NotEmpty
    @Size(min = 4, max = 50)
    private String username;

    @NotEmpty
    @Size(min = 6, max = 50)
    private String password;

    @NotNull
    @Valid // @Valid takes the annotations in RoleDto (NotEmpty, size....etc) into consideration.
    private List<RoleDto> roles;
}
