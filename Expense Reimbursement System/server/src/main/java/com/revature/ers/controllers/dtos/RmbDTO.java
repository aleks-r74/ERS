package com.revature.ers.controllers.dtos;

import com.revature.ers.entities.User;
import com.revature.ers.entities.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class RmbDTO {
    private String description;
    private Integer amount;
    public boolean valid(){
        return description!=null
                && !description.isBlank()
                && amount!=null;
    }
}
