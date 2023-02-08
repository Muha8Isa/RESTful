package se.lexicon.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.lexicon.restful.model.entity.dto.UserDto;
import se.lexicon.restful.service.UserService;

import java.util.Map;

@Controller
@ResponseBody
// @RestController = @Controller + @ResponseBody
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired UserService userService;

    //sign-up: request body -- POST
    @PostMapping("/")
    //@RequestMapping(path = "/", method = RequestMethod.POST) //Alternative for @PostMapping
    public ResponseEntity<UserDto> signup(@RequestBody UserDto dto){
        System.out.println("Username: " + dto.getUsername());
        UserDto serviceResult = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceResult);
    }

    // GET http://localhost:8080/api/v1/user/admin
    // /{username}  search: path variable -- GET
    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> findByUsername(@PathVariable("username") String username){
    return ResponseEntity.ok().body(userService.findByUsername(username));
    }

    // /{username}  disable user: path variable -- PUT
    @PutMapping("/disable")
    public ResponseEntity<Void> disableUserByUsername(@RequestParam("username") String username){
        userService.disableUserByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
