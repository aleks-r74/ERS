package com.revature.ers.entities;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.revature.ers.entities.enums.TicketStatus;
import com.revature.ers.utilities.CustomJSONUserSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="reimbursement")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Reimbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reimbId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId") // foreign key in User class
    @JsonSerialize(using= CustomJSONUserSerializer.class)
    private User user;
}
