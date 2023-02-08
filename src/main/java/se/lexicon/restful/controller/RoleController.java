package se.lexicon.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    //   return ResponseEntity.ok(roleService.getAll()); //return data for List<Role>. The ok means that http response code is 200
        // This will make sure that the request is successful, if it is, the method will be applied. Otherwise, error message will be displayed.
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable("id") Integer id){
       // RoleDto result = new RoleDto(id,"Test");
        return ResponseEntity.ok(roleService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){ // Delete is a void method; there is no return type.
    roleService.delete(id);
    //return ResponseEntity.noContent().build(); // void method has no body. No body means 204.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Alternative method.
    }

    @PostMapping("/")
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto dto){
    RoleDto createdRoleDto = roleService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdRoleDto); // 201
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody RoleDto dto){
        roleService.update(dto);
        return ResponseEntity.noContent().build();
    }
}
