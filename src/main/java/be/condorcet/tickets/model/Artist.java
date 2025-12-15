package be.condorcet.tickets.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Artist {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;
}
