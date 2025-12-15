package be.condorcet.tickets.service;

import be.condorcet.tickets.*;
import be.condorcet.tickets.model.Concert;
import be.condorcet.tickets.model.Order;
import be.condorcet.tickets.model.Ticket;
import be.condorcet.tickets.repository.ConcertRepository;
import be.condorcet.tickets.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final ConcertRepository concertRepo;
    private final TicketRepository ticketRepo;

    public TicketService(ConcertRepository concertRepo, TicketRepository ticketRepo) {
        this.concertRepo = concertRepo;
        this.ticketRepo = ticketRepo;
    }

    @Transactional
    public Ticket buyTicket(Long concertId, Order order) {
        Concert concert = concertRepo.findById(concertId)
                .orElseThrow();

        if (concert.getAvailableTickets() <= 0) {
            throw new RuntimeException("No tickets left");
        }

        concert.setAvailableTickets(concert.getAvailableTickets() - 1);

        Ticket ticket = new Ticket();
        ticket.setConcert(concert);
        ticket.setOrder(order);

        return ticketRepo.save(ticket);
    }
}
