package com.revature.ers.services.contracts;

import com.revature.ers.controllers.dtos.UserDTO;
import com.revature.ers.entities.User;

import java.util.List;

public interface UserService {
    User create(User user);
    List<User> getAll();
    void delete(Integer userId);
    void update(User user); // update role and other info
    User findByUsername(String username);
    void login(UserDTO credentials);
    void logout();
    public default boolean isValid(User user){
        return user != null
                && user.getFirstName()!=null
                && !user.getFirstName().isBlank()
                && user.getLastName()!=null
                && !user.getLastName().isBlank()
                && user.getUsername()!=null
                && !user.getUsername().isBlank()
                && user.getPassword()!=null
                && !user.getPassword().isBlank()
                && user.getRole()!=null;
    }
}
