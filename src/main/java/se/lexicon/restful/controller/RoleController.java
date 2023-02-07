package se.lexicon.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.restful.model.entity.Role;
import se.lexicon.restful.model.entity.dto.RoleDto;
import se.lexicon.restful.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {


    @Autowired
    RoleService roleService;
    // http://localhost:8080/api/v1/role/   URI
    @GetMapping("/")
    public ResponseEntity<List<RoleDto>> getAll(){ // ResponseEntity<> converts List<Role> to a JSON message, where List<Role> is the message body.
    //  List<RoleDto> roles = new ArrayList<>();
    //  roles.add(new RoleDto(1,"ADMIN"));
    //  roles.add(new RoleDto(2,"USER"));
    return ResponseEntity.ok(roleService.getAll()); //return data for List<Role>. The ok means that http response code is 200
        // This will make sure that the request is successful, if it is, the method will be applied. Otherwise, error message will be displayed.

    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable("id") Integer id){
       // RoleDto result = new RoleDto(id,"Test");
        return ResponseEntity.ok(roleService.findById(id));
    }
}
