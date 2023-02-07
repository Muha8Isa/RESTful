package se.lexicon.restful.model.entity.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto { //DTO is used to reduce the number of methods. Inside the entity, focus on entity part; database communication.
    // In DTO focus on JSON message structure and validate it.
    private int id;
    private String name;
}
