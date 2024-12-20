package com.revature.ers.services.contracts;

import com.revature.ers.ErsApplication;
import com.revature.ers.entities.Reimbursement;
import com.revature.ers.entities.User;
import com.revature.ers.entities.enums.TicketStatus;
import com.revature.ers.entities.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = ErsApplication.class)
class ReimbursementServiceTest {
    @Autowired
    ReimbursementService reimbursementService;
    @Autowired
    UserService userService;

    @Test
    void create() {
        User user = new User(null, "firstName", "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
        user = userService.create(user);

        var reimb = new Reimbursement();
        reimb.setUser(user);
        reimb.setDescription("Test description");
        reimb.setAmount(5);
        reimb.setStatus(TicketStatus.PENDING);
        reimbursementService.create(reimb);
        assertTrue(reimbursementService.getTickets().size()==1);
    }

    @Test
    void update() {
//        assertTrue(reimbursementService.getTickets().isEmpty());
//        // create user
//        User user = new User(null, "firstName", "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
//        user = userService.create(user);
//        // create reimb
//        var reimb = new Reimbursement();
//        reimb.setUser(user);
//        reimb.setDescription("Test description");
//        reimb.setAmount(5);
//        reimb.setStatus(TicketStatus.PENDING);
//        reimbursementService.create(reimb);
//        assertTrue(reimbursementService.getTickets().size()==1);
//        // update description
//        String updDescription = "updated";
//        reimb = reimbursementService.getTickets().get(0);
//        reimbursementService.update(reimb.getReimbId(),updDescription);
//        reimb = reimbursementService.getTickets().get(0);
//        assertEquals(reimb.getDescription(),updDescription);
//        // update status
//        reimbursementService.update(reimb.getReimbId(),TicketStatus.DENIED);
//        reimb = reimbursementService.getTickets().get(0);
//        assertEquals(reimb.getStatus(),TicketStatus.DENIED);
    }

    @Test
    void testGetTickets() {
//        assertTrue(reimbursementService.getTickets().isEmpty());
//
//        User user1 = new User(null, "firstName", "LastName", "Username", "Password", UserRole.EMPLOYEE, null);
//        User user2 = new User(null, "firstName2", "LastName2", "Username2", "Password2", UserRole.MANAGER, null);
//        user1 = userService.create(user1);
//        user2 = userService.create(user2);
//        // reimb for user 1
//        var reimb1 = new Reimbursement();
//        reimb1.setUser(user1);
//        reimb1.setDescription("Test description");
//        reimb1.setAmount(5);
//        reimb1.setStatus(TicketStatus.APPROVED);
//        reimbursementService.create(reimb1);
//
//        // reimb for user 2
//        var reimb2 = new Reimbursement();
//        reimb2.setUser(user2);
//        reimb2.setDescription("Test description");
//        reimb2.setAmount(15);
//        reimb2.setStatus(TicketStatus.DENIED);
//        reimbursementService.create(reimb2);
//
//        assertTrue(reimbursementService.getTickets().size()==2,"2 tickets total");
//        assertTrue(reimbursementService.getTickets(user1.getUserId()).size()==1,"user 1 has 1 ticket");
//        assertTrue(reimbursementService.getTickets(TicketStatus.PENDING).isEmpty(),"no pending tickets");
//        assertTrue(reimbursementService.getTickets(TicketStatus.APPROVED).size()==1,"1 approved ticket");
//        // update status and find again
//        var tickets = reimbursementService.getTickets();
//        for(var ticket: tickets)
//            reimbursementService.update(ticket.getReimbId(),TicketStatus.PENDING);
//        assertTrue(reimbursementService.getTickets(TicketStatus.PENDING).size()==2,"2 pending tickets");
    }

    @Test
    void isValidReimbursement() {
        User user1 = new User(null, "firstName2", "LastName2", "Username2", "Password2", UserRole.MANAGER, null);
        user1 = userService.create(user1);
        var reimb = new Reimbursement();
        reimb.setUser(user1);
        reimb.setDescription("Test description");
        reimb.setAmount(15);
        reimb.setStatus(TicketStatus.DENIED);
        assertTrue(reimbursementService.isValidReimbursement(reimb));
        reimb.setDescription(null);
        assertFalse(reimbursementService.isValidReimbursement(reimb));
    }
}