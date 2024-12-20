package com.revature.ers.services;

import com.revature.ers.controllers.dtos.UserDTO;
import com.revature.ers.controllers.exceptions.NotAuthorizedException;
import com.revature.ers.repositories.UserRepo;
import com.revature.ers.entities.User;
import com.revature.ers.entities.enums.UserRole;
import com.revature.ers.security.AuthContext;
import com.revature.ers.services.contracts.UserService;
import com.revature.ers.utilities.aop.Authenticate;
import com.revature.ers.utilities.aop.Authorize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    private AuthContext authContext;

    @Override
    public User create(User user) {
        if(!isValid(user)) throw new IllegalArgumentException("Invalid user object");
        if(user.getRole()==UserRole.MANAGER && authContext.getRole() != UserRole.MANAGER)
            throw new NotAuthorizedException("You're not authorized for this operation");
        if(userRepo.findByUsername(user.getUsername())!=null) throw new IllegalArgumentException("Such user exists");
        return userRepo.save(user);
    }

    @Override
    @Authenticate
    public List<User> getAll() {
        var result =  userRepo.findAll();
        for(var user: result)
            user.setPassword("");
        return result;
    }

    @Override
    @Authorize(role=UserRole.MANAGER)
    public void delete(Integer userId) {
        checkDbPresence(userId);
        userRepo.deleteById(userId);
    }

    @Override
    @Authorize(role=UserRole.MANAGER)
    public void update(User user) {
        if(!isValid(user) || user.getUserId() == null)
            throw new IllegalArgumentException("Invalid user object");
        checkDbPresence(user.getUserId());
        userRepo.save(user);

    }

    @Override
    public User findByUsername(String username) {
        if(username==null) throw new IllegalArgumentException("Username can not be empty");
        return userRepo.findByUsername(username);
    }

    @Override
    public void login(UserDTO credentials) {
        var dbUser = userRepo.findByUsername(credentials.getUsername());
        if(dbUser==null || !dbUser.getPassword().equals(credentials.getPassword()))
            throw new IllegalArgumentException("There is no such user");
        authContext.setRole(dbUser.getRole());
        authContext.setUsername(dbUser.getUsername());
        authContext.setUserId(dbUser.getUserId());
    }

    @Override
    public void logout() {
        authContext.reset();
    }

    private void checkDbPresence(Integer userId){
        if(!userRepo.existsById(userId))
            throw new IllegalArgumentException("User with id %d not found in the DB".formatted(userId));
    }
}
