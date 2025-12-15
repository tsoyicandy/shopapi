package be.condorcet.tickets.controller;


import be.condorcet.tickets.model.Concert;
import be.condorcet.tickets.repository.ConcertRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertRepository repo;

    public ConcertController(ConcertRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Concert> list() {
        return repo.findAll();
    }

    @PostMapping
    public Concert create(@Valid @RequestBody Concert c) {
        return repo.save(c);
    }
}

