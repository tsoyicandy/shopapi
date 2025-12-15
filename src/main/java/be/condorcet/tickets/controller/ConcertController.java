package be.condorcet.tickets.controller;

import be.condorcet.tickets.model.Concert;
import be.condorcet.tickets.repository.ConcertRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertRepository concertRepository;

    public ConcertController(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    // GET /api/concerts
    @GetMapping
    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    // GET /api/concerts/{id}
    @GetMapping("/{id}")
    public Concert getConcertById(@PathVariable Long id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found"));
    }

    // POST /api/concerts (ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Concert createConcert(@Valid @RequestBody Concert concert) {
        return concertRepository.save(concert);
    }

    // PUT /api/concerts/{id}
    @PutMapping("/{id}")
    public Concert updateConcert(
            @PathVariable Long id,
            @Valid @RequestBody Concert updatedConcert
    ) {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        concert.setTitle(updatedConcert.getTitle());
        concert.setAvailableTickets(updatedConcert.getAvailableTickets());
        concert.setArtist(updatedConcert.getArtist());

        return concertRepository.save(concert);
    }

    // DELETE /api/concerts/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConcert(@PathVariable Long id) {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        concertRepository.delete(concert);
    }
}
