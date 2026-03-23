package dev_grachev.spring_learn_project_3.controller;

import dev_grachev.spring_learn_project_3.dto.CreateMovieRequest;
import dev_grachev.spring_learn_project_3.dto.MovieResponse;
import dev_grachev.spring_learn_project_3.dto.UpdateMovieRequest;
import dev_grachev.spring_learn_project_3.service.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return new ResponseEntity<>(movieService.findAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id){
        return movieService.findMovieById(id)
                .map(movieDto -> new ResponseEntity<>(movieDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/random")
    public ResponseEntity<MovieResponse> getRandomMovie() {
        return movieService.findRandomMovie()
                .map(movieDto -> new ResponseEntity<>(movieDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/director/{director}")
    public ResponseEntity<List<MovieResponse>> getAllMoviesByDirector(@PathVariable String director) {
        return new ResponseEntity<>(movieService.findAllMoviesByDirector(director), HttpStatus.OK);
    }

    @GetMapping("/{genre}/count")
    public ResponseEntity<Integer> getMovieCountByGenre(@PathVariable String genre) {
        return new ResponseEntity<>(movieService.findCountMoviesByGenre(genre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
        return new ResponseEntity<>(movieService.registerMovie(createMovieRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @Valid @RequestBody UpdateMovieRequest updateMovieRequest) {
        return new ResponseEntity<>(movieService.updateMovie(id, updateMovieRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id){
        return movieService.deleteMovie(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
