package com.revature.ers.controllers;

import com.revature.ers.controllers.dtos.RmbDTO;
import com.revature.ers.controllers.dtos.RmbUpdDTO;
import com.revature.ers.entities.Reimbursement;
import com.revature.ers.entities.enums.TicketStatus;
import com.revature.ers.entities.enums.UserRole;
import com.revature.ers.security.AuthContext;
import com.revature.ers.services.contracts.ReimbursementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rmbs")
@AllArgsConstructor
public class ReimbursementController {
    private ReimbursementService reimbursementService;
    private AuthContext authContext;

    @PostMapping
    ResponseEntity<Void> create(@RequestBody RmbDTO dto){
        if(!dto.valid()) throw new IllegalArgumentException("Invalid reimbursement DTO object");
        var rmb = new Reimbursement(null,dto.getDescription(),dto.getAmount(),TicketStatus.PENDING,null);
        reimbursementService.create(rmb, authContext.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<Reimbursement>> getAll(@RequestParam(name = "status", required = false) String status){
        if(status != null){
            var requestedStatus = TicketStatus.valueOf(status.trim());
            if(authContext.getRole() == UserRole.MANAGER)
                return ResponseEntity.ok(reimbursementService.getTickets(requestedStatus));
            return ResponseEntity.ok(reimbursementService.getTickets(authContext.getUserId(),requestedStatus));
        } else {
            if(authContext.getRole() == UserRole.MANAGER)
                return ResponseEntity.ok(reimbursementService.getTickets());
            return ResponseEntity.ok(reimbursementService.getTickets(authContext.getUserId()));
        }
    }

    @PatchMapping
    ResponseEntity<Void> updateRmb(@RequestBody RmbUpdDTO dto){
        if(!dto.isValid())
            return ResponseEntity.badRequest().build();
        reimbursementService.update(dto);
        return ResponseEntity.ok().build();
    }
}
