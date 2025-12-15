package be.condorcet.tickets.controller;

import be.condorcet.tickets.model.Order;
import be.condorcet.tickets.model.Ticket;
import be.condorcet.tickets.repository.OrderRepository;
import be.condorcet.tickets.service.TicketService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;
    private final OrderRepository orderRepo;

    public TicketController(TicketService service, OrderRepository orderRepo) {
        this.service = service;
        this.orderRepo = orderRepo;
    }

    @PostMapping("/buy/{concertId}")
    public Ticket buy(@PathVariable Long concertId, @RequestParam Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        return service.buyTicket(concertId, order);
    }
}

