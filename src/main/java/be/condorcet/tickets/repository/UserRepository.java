package be.condorcet.tickets.repository;

import be.condorcet.tickets.model.Ticket;
import be.condorcet.tickets.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
