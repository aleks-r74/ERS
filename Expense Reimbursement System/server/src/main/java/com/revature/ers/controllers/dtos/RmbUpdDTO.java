package com.revature.ers.controllers.dtos;

import com.revature.ers.entities.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class RmbUpdDTO {
    private Integer reimbId;
    private String status;
    private String description;
    public boolean isValid(){
        return reimbId != null && reimbId > 0
                && status != null
                && description !=null && !description.isBlank();
    }
}
