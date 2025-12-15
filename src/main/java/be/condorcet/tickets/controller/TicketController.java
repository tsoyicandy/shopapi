package be.condorcet.tickets.controller;

import be.condorcet.tickets.model.Concert;
import be.condorcet.tickets.model.Order;
import be.condorcet.tickets.model.Ticket;
import be.condorcet.tickets.repository.ConcertRepository;
import be.condorcet.tickets.repository.OrderRepository;
import be.condorcet.tickets.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final ConcertRepository concertRepository;
    private final OrderRepository orderRepository;

    public TicketController(
            TicketRepository ticketRepository,
            ConcertRepository concertRepository,
            OrderRepository orderRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.concertRepository = concertRepository;
        this.orderRepository = orderRepository;
    }

    // ======================
    // CRUD STANDARD
    // ======================

    /**
     * GET /api/tickets
     * Récupérer tous les tickets
     */
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    /**
     * GET /api/tickets/{id}
     * Récupérer un ticket par ID
     */
    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    /**
     * POST /api/tickets
     * Créer un ticket (ADMIN)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket createTicket(
            @RequestParam Long concertId,
            @RequestParam Long orderId
    ) {
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Ticket ticket = new Ticket();
        ticket.setConcert(concert);
        ticket.setOrder(order);

        return ticketRepository.save(ticket);
    }

    /**
     * PUT /api/tickets/{id}
     * Modifier un ticket
     */
    @PutMapping("/{id}")
    public Ticket updateTicket(
            @PathVariable Long id,
            @RequestParam Long concertId,
            @RequestParam Long orderId
    ) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        ticket.setConcert(concert);
        ticket.setOrder(order);

        return ticketRepository.save(ticket);
    }

    /**
     * DELETE /api/tickets/{id}
     * Supprimer un ticket
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticketRepository.delete(ticket);
    }

    // ======================
    // MÉTIER : ACHAT
    // ======================

    /**
     * POST /api/tickets/buy/{concertId}?orderId=X
     * Achat d’un ticket (USER)
     */
    @PostMapping("/buy/{concertId}")
    @Transactional
    public Ticket buyTicket(
            @PathVariable Long concertId,
            @RequestParam Long orderId
    ) {
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        if (concert.getAvailableTickets() <= 0) {
            throw new RuntimeException("No tickets available for this concert");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Règle métier
        concert.setAvailableTickets(concert.getAvailableTickets() - 1);

        Ticket ticket = new Ticket();
        ticket.setConcert(concert);
        ticket.setOrder(order);

        return ticketRepository.save(ticket);
    }
}
