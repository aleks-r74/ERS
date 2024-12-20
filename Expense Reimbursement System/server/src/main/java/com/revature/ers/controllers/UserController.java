package com.revature.ers.controllers;

import com.revature.ers.controllers.dtos.UserDTO;
import com.revature.ers.entities.User;
import com.revature.ers.entities.enums.UserRole;
import com.revature.ers.security.AuthContext;
import com.revature.ers.services.contracts.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private AuthContext authContext;

    @GetMapping
    ResponseEntity<List<User>> getUsers(){
        var users = userService.getAll();
        users.sort(Comparator.comparing(User::getUserId));
        return ResponseEntity.ok(users);
    }

    @PostMapping
    ResponseEntity<User> createUser(@RequestBody User user){
        if(user.getRole()==null) user.setRole(UserRole.EMPLOYEE);
        if(!userService.isValid(user)) throw new IllegalArgumentException("Invalid user object");
        User createdUser = userService.create(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getUserId())
                .toUri();
        userService.login(new UserDTO(user.getUsername(), user.getPassword(), null));
        return ResponseEntity.created(location).body(createdUser);
    }

    @PatchMapping
    ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO){
        User dbUser = userService.findByUsername(userDTO.getUsername());
        if(userDTO.getPassword()!=null)
            dbUser.setPassword(userDTO.getPassword());
        if(userDTO.getRole()!=null)
            dbUser.setRole(UserRole.valueOf(userDTO.getRole()));
        userService.update(dbUser);
        return ResponseEntity.ok(dbUser);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer userId){
        userService.delete(userId);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/login")
    ResponseEntity<UserDTO> authenticateUser(@RequestBody UserDTO credentials){
        userService.login(credentials);
        var user = new UserDTO(authContext.getUsername(),null, authContext.getRole().toString());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/logout")
    ResponseEntity<Void> logout(){
        userService.logout();
        return ResponseEntity.ok().build();
    }
}
