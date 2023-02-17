package se.lexicon.restful.model.entity.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto { //DTO is used to reduce the number of methods. Inside the entity, focus on entity part; database communication.
    // In DTO focus on JSON message structure and validate it.
    private int id;


    //@NotEmpty(message = "name should not be empty")
    //@Size(min = 2, max = 40, message = "name length should be between 2 and 40")
    @NotEmpty
    @Size(min = 2, max = 40) // NotEmpty and Size have custom messages.
    private String name;
}
