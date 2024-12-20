package com.revature.ers.repositories;

import com.revature.ers.ErsApplication;
import com.revature.ers.entities.Reimbursement;
import com.revature.ers.entities.User;
import com.revature.ers.entities.enums.TicketStatus;
import com.revature.ers.entities.enums.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ContextConfiguration(classes = ErsApplication.class)
class ReimbursementRepoTest {
    @Autowired
    ReimbursementRepo reimbursementRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    EntityManager entityManager;

    @Test
    void createReimbTest(){
        var reimb = new Reimbursement();
        reimb.setDescription("Test description");
        reimb.setAmount(5);
        reimb.setStatus(TicketStatus.PENDING);
        var reimbDb = reimbursementRepo.save(reimb);
        assertNotNull(reimbDb);
    }

    @Test
    void deleteReimbTest(){
        // create reimbursement
        var reimb = new Reimbursement();
        reimb.setDescription("Test description");
        reimb.setAmount(5);
        reimb.setStatus(TicketStatus.PENDING);
        var reimbDb = reimbursementRepo.save(reimb);
        assertNotNull(reimbDb,"saved to db");
        // delete
        reimbursementRepo.delete(reimbDb);
        // validate
        assertFalse(reimbursementRepo.existsById(reimbDb.getReimbId()),"deleted from db");
    }

    @Test
    void findByUserUserId() {
        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setRole(UserRole.EMPLOYEE);
        user.setUsername("username");
        user.setPassword("password");
        user = userRepo.save(user);
        assertNotNull(user.getUserId(),"user saved");

        Reimbursement rmb = new Reimbursement();
        rmb.setUser(user);
        rmb.setDescription("rmb description");
        rmb.setAmount(1);
        rmb.setStatus(TicketStatus.PENDING);
        reimbursementRepo.save(rmb);
        // find reimbursement by userId
        assertFalse(reimbursementRepo.findByUserUserId(user.getUserId()).isEmpty(),"rmb found");
    }

    @Test
    void findByStatus() {
        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setRole(UserRole.EMPLOYEE);
        user.setUsername("username");
        user.setPassword("password");
        user = userRepo.save(user);
        assertNotNull(user.getUserId(),"user saved");

        Reimbursement rmb = new Reimbursement();
        rmb.setUser(user);
        rmb.setDescription("rmb description");
        rmb.setAmount(1);
        rmb.setStatus(TicketStatus.PENDING);
        reimbursementRepo.save(rmb);
        assertTrue(reimbursementRepo.findByStatus(TicketStatus.DENIED).isEmpty(),"No tickets with DENIED status");

        // update ticket status and try again
        rmb.setStatus(TicketStatus.DENIED);
        reimbursementRepo.save(rmb);
        assertFalse(reimbursementRepo.findByStatus(TicketStatus.DENIED).isEmpty(),"Ticket with DENIED status found");
    }

    @Test
    void findByStatusAndUserUserId() {
        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setRole(UserRole.EMPLOYEE);
        user.setUsername("username");
        user.setPassword("password");
        user = userRepo.save(user);
        assertNotNull(user.getUserId(),"user saved");

        Reimbursement rmb = new Reimbursement();
        rmb.setUser(user);
        rmb.setDescription("rmb description");
        rmb.setAmount(1);
        rmb.setStatus(TicketStatus.PENDING);
        reimbursementRepo.save(rmb);
        // detach rmb from the DB to make a modified copy of the entity
        entityManager.detach(rmb);
        rmb.setReimbId(null);
        rmb.setStatus(TicketStatus.DENIED);
        reimbursementRepo.save(rmb);

        assertEquals(2, reimbursementRepo.findAll().size(), "Two tickets total");
        assertTrue(reimbursementRepo.findByStatusAndUserUserId(TicketStatus.APPROVED,user.getUserId()).isEmpty(),"No APPROVED tickets");
        assertEquals(1, reimbursementRepo.findByStatusAndUserUserId(TicketStatus.DENIED, user.getUserId()).size(), "1 DENIED ticket");


    }
}