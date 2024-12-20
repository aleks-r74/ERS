package com.revature.ers.repositories;

import com.revature.ers.entities.Reimbursement;
import com.revature.ers.entities.enums.TicketStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementRepo extends JpaRepository<Reimbursement, Integer> {
    public List<Reimbursement> findByUserUserId(Integer userId, Sort sort);
    public List<Reimbursement> findByStatus(TicketStatus ticketStatus, Sort sort);
    List<Reimbursement> findByStatusAndUserUserId(TicketStatus status, Integer userId, Sort sort);

}
