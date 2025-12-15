package be.condorcet.tickets.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Concert concert;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}

