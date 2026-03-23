package dev_grachev.spring_learn_project_3.service;

import dev_grachev.spring_learn_project_3.dto.CreateMovieRequest;
import dev_grachev.spring_learn_project_3.dto.MovieResponse;
import dev_grachev.spring_learn_project_3.dto.UpdateMovieRequest;
import dev_grachev.spring_learn_project_3.model.Movie;
import dev_grachev.spring_learn_project_3.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final Random random = new Random();

    public List<MovieResponse> findAllMovies() {
        return movieRepository.findAll().stream().map(this::convertToDto).toList();
    }

    public Optional<MovieResponse> findMovieById(Long id){
        return movieRepository.findById(id).map(this::convertToDto);
    }

    public Optional<MovieResponse> findRandomMovie() {
        List<Movie> movies = movieRepository.findAll();

        if(movies.isEmpty()) return Optional.empty();

        long randomIndex = random.nextInt(movies.size());
        return movieRepository.findById(randomIndex).map(this::convertToDto);
    }

    public List<MovieResponse> findAllMoviesByDirector(String director){
        return movieRepository.findAllByDirector(director).stream().map(this::convertToDto).toList();
    }

    public int findCountMoviesByGenre(String genre){
        return movieRepository.countByGenre(genre);
    }

    public MovieResponse registerMovie(CreateMovieRequest request) {
        Movie movie = new Movie();
        populateMovieByRequestData(movie, request.getTitle(), request.getDirector(), request.getGenre(), request.getReleaseYear());

        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie);
    }

    public void updateMovie(Long id, UpdateMovieRequest request) {
        Movie movie = movieRepository.findById(id).orElseThrow();

        populateMovieByRequestData(movie, request.getTitle(), request.getDirector(), request.getGenre(), request.getReleaseYear());

        movieRepository.save(movie);
    }

    public Boolean deleteMovie(Long id){
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    private void populateMovieByRequestData(Movie movie, String title, String director, String genre, int releaseYear){
        movie.setDirector(director.strip());
        movie.setTitle(title.strip());

        movie.setReleaseYear(Year.of(releaseYear));

        if(genre.isBlank()){
            movie.setGenre(null);
        }
        else{
            movie.setGenre(genre.strip());
        }
    }

    private MovieResponse convertToDto(Movie movie){
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(movie.getId());
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setDirector(movie.getDirector());
        movieResponse.setGenre(movie.getGenre());
        movieResponse.setReleaseYear(movie.getReleaseYear().getValue());
        return movieResponse;
    }
}
