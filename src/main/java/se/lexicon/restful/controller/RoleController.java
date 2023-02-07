package se.lexicon.restful.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.restful.model.entity.Role;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleController {

    // http://localhost:8080/api/v1/role/   URI
    @GetMapping("/api/v1/role/")
    public ResponseEntity<List<Role>> getAll(){ // ResponseEntity<> converts List<Role> to a JSON message, where List<Role> is the message body.
    List<Role> roles = new ArrayList<>();
    roles.add(new Role(1,"ADMIN"));
    roles.add(new Role(2,"USER"));
    return ResponseEntity.ok(roles); //return data for List<Role>. The ok means that http response code is 200
        // This will make sure that the request is successful, if it is, the method will be applied. Otherwise, error message will be displayed.

    }
}
