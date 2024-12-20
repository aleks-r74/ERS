package com.revature.ers.repositories;


import com.revature.ers.ErsApplication;
import com.revature.ers.entities.User;
import com.revature.ers.entities.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ContextConfiguration(classes = ErsApplication.class)
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void createUserTest() {
        User user1 = new User(null, "FirstName", "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
        user1 = userRepo.save(user1);
        assertNotNull(user1.getUserId(), "User ID should not be null after saving.");
    }

    @Test
    public void deleteUserTest(){

        // create user
        User user1 = new User(null, "FirstName", "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
        user1 = userRepo.save(user1);
        assertNotNull(user1.getUserId(), "User ID should not be null after saving.");
        // load user from db
        var userDb = userRepo.findById(user1.getUserId());
        assertTrue(userDb.isPresent(), "User found in the DB");
        // delete user
        userRepo.delete(userDb.get());
        userDb = userRepo.findById(user1.getUserId());
        assertTrue(userDb.isEmpty(), "User deleted");

    }
}
