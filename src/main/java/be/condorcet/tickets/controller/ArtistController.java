package be.condorcet.tickets.controller;

import be.condorcet.tickets.model.Artist;
import be.condorcet.tickets.repository.ArtistRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository artistRepository;

    public ArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    // GET /api/artists
    @GetMapping
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    // GET /api/artists/{id}
    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
    }

    // POST /api/artists (ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@Valid @RequestBody Artist artist) {
        return artistRepository.save(artist);
    }

    // PUT /api/artists/{id}
    @PutMapping("/{id}")
    public Artist updateArtist(
            @PathVariable Long id,
            @Valid @RequestBody Artist updatedArtist
    ) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        artist.setName(updatedArtist.getName());

        return artistRepository.save(artist);
    }

    // DELETE /api/artists/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        artistRepository.delete(artist);
    }
}
