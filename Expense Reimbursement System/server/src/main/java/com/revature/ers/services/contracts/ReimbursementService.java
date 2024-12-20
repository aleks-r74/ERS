package com.revature.ers.services.contracts;

import com.revature.ers.controllers.dtos.RmbUpdDTO;
import com.revature.ers.entities.Reimbursement;
import com.revature.ers.entities.enums.TicketStatus;

import java.util.List;

public interface ReimbursementService {
    void create(Reimbursement r, Integer... userId);
    void update(RmbUpdDTO dto);
    List<Reimbursement> getTickets();
    List<Reimbursement> getTickets(Integer userId);
    List<Reimbursement> getTickets(TicketStatus status);
    List<Reimbursement> getTickets(Integer userId, TicketStatus status);

    public default boolean isValidReimbursement(Reimbursement r){
        // returns true if all the conditions are true
        return r != null
                && r.getUser() != null
                && r.getAmount() > 0
                && r.getDescription() != null
                && !r.getDescription().isBlank();
    }
}
