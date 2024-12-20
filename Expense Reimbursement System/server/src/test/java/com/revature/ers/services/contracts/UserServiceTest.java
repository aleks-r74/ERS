package com.revature.ers.services.contracts;

import com.revature.ers.ErsApplication;
import com.revature.ers.entities.User;
import com.revature.ers.entities.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = ErsApplication.class)
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void create() {
        String firstName = "FirstNameService";
        User user1 = new User(null, firstName, "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
        userService.create(user1);
        var dbResult = userService.getAll().stream().filter(u->u.getFirstName().equals(firstName)).findAny();
        assertTrue(dbResult.isPresent(),"User saved through service");
    }

    @Test
    void getAll() {
        assertTrue(userService.getAll().isEmpty());
        User user1 = new User(null, "firstName", "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
        userService.create(user1);
        assertTrue(!userService.getAll().isEmpty(),"User found");
    }

    @Test
    void delete() {
        User user1 = new User(null, "firstName", "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
        userService.create(user1);
        var dbResult = userService.getAll().stream().filter(u->u.getFirstName().equals("firstName")).findAny();
        assertTrue(dbResult.isPresent(),"User saved through service");
        // delete
        userService.delete(dbResult.get().getUserId());
        dbResult = userService.getAll().stream().filter(u->u.getFirstName().equals("firstName")).findAny();
        assertTrue(dbResult.isEmpty());
    }

    @Test
    void update() {
        String firstName = "FirstNameService";
        String updatedFirstName = "UpdatedFirstNameService";
        User user1 = new User(null, firstName, "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
        userService.create(user1);
        var dbResult = userService.getAll().stream().filter(u->u.getFirstName().equals(firstName)).findAny();
        assertTrue(dbResult.isPresent(),"User saved through service");
        // update
        dbResult.get().setFirstName(updatedFirstName);
        userService.update(dbResult.get());
        dbResult = userService.getAll().stream().filter(u->u.getFirstName().equals(updatedFirstName)).findAny();
        assertTrue(dbResult.isPresent(),"User updated through service");
    }

    @Test
    void isValid() {
        User user = new User(null, "firstName", "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
        assertTrue(userService.isValid(user),"valid user objejct");
        user.setFirstName(null);
        assertFalse(userService.isValid(user),"invalid user object");
    }
}