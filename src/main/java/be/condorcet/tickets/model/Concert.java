package be.condorcet.tickets.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
public class Concert {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    @Positive
    private int availableTickets;

    @ManyToOne
    private Artist artist;
}
