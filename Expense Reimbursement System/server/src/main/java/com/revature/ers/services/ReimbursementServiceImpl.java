package com.revature.ers.services;

import com.revature.ers.controllers.dtos.RmbUpdDTO;
import com.revature.ers.controllers.exceptions.NotAuthorizedException;
import com.revature.ers.repositories.ReimbursementRepo;
import com.revature.ers.repositories.UserRepo;
import com.revature.ers.entities.Reimbursement;
import com.revature.ers.entities.User;
import com.revature.ers.entities.enums.TicketStatus;
import com.revature.ers.entities.enums.UserRole;
import com.revature.ers.security.AuthContext;
import com.revature.ers.services.contracts.ReimbursementService;
import com.revature.ers.utilities.aop.Authenticate;
import com.revature.ers.utilities.aop.Authorize;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReimbursementServiceImpl implements ReimbursementService {
    private ReimbursementRepo reimbursementRepo;
    private UserRepo userRepo;
    private AuthContext authContext;

    @Authorize
    @Override
    public void create(Reimbursement r, Integer... userId) {
        if(userId.length>0){
            if(!userRepo.existsById(userId[0])) throw new IllegalArgumentException("User with id %d not found");
            var user = new User();
            user.setUserId(userId[0]);
            r.setUser(user);
        }
        if(!isValidReimbursement(r)) throw new IllegalArgumentException("Invalid reimbursement object");
        reimbursementRepo.save(r);
    }

    @Authenticate
    @Override
    public void update(RmbUpdDTO dto) {
        var reimb = getReimbursement(dto.getReimbId());
        if(reimb.getStatus()!=TicketStatus.PENDING && authContext.getRole() == UserRole.EMPLOYEE)
            throw new NotAuthorizedException("You can update only PENDING tickets");
        if(authContext.getRole() == UserRole.MANAGER)
            reimb.setStatus(TicketStatus.valueOf(dto.getStatus()));
        reimb.setDescription(dto.getDescription());
        reimbursementRepo.save(reimb);
    }


    @Override
    @Authenticate
    public List<Reimbursement> getTickets() {
        return reimbursementRepo.findAll(Sort.by(Sort.Order.asc("reimbId")));
    }

    @Override
    @Authenticate
    public List<Reimbursement> getTickets(Integer userId) {
        return reimbursementRepo.findByUserUserId(userId,Sort.by(Sort.Order.asc("reimbId")));
    }

    @Override
    @Authenticate
    public List<Reimbursement> getTickets(TicketStatus status) {
        return reimbursementRepo.findByStatus(status,Sort.by(Sort.Order.asc("reimbId")));
    }

    @Override
    @Authenticate
    public List<Reimbursement> getTickets(Integer userId, TicketStatus status) {
        return reimbursementRepo.findByStatusAndUserUserId(status,userId,Sort.by(Sort.Order.asc("reimbId")));
    }

    private Reimbursement getReimbursement(Integer reimbId) {
        Optional<Reimbursement> reimb = reimbursementRepo.findById(reimbId);
        if(reimb.isEmpty()) throw new IllegalArgumentException("Reimbursment with Id %d not found".formatted(reimbId));
        return reimb.get();
    }

}
