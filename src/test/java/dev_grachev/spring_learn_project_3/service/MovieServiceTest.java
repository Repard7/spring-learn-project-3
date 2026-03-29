package dev_grachev.spring_learn_project_3.service;

import dev_grachev.spring_learn_project_3.dto.CreateMovieRequest;
import dev_grachev.spring_learn_project_3.dto.MovieResponse;
import dev_grachev.spring_learn_project_3.model.Movie;
import dev_grachev.spring_learn_project_3.repository.MovieRepository;
import dev_grachev.spring_learn_project_3.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie testMovie;
    private CreateMovieRequest createRequest;

    @BeforeEach
    void setUp(){
        testMovie = new Movie("Inception", "Christopher Nolan", Year.of(2010), "Sci-Fi");
        testMovie.setId(1L);

        createRequest = new CreateMovieRequest();
        createRequest.setGenre("Sci-Fi");
        createRequest.setTitle("Inception");
        createRequest.setDirector("Christopher Nolan");
        createRequest.setReleaseYear(2010);
    }

    @Test
    void findAllMovies_shouldReturnListOfMoviesResponses(){
        when(movieRepository.findAll()).thenReturn(List.of(testMovie));

        List<MovieResponse> result = movieService.findAllMovies();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Inception");
        verify(movieRepository, times(1)).findAll();
    }
    @Test
    void findMovieById_whenExists_shouldReturnOptionalMovieResponse(){
        when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));

        Optional<MovieResponse> result = movieService.findMovieById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(movieRepository).findById(1L);
    }
    @Test
    void findMovieById_whenNotExists_shouldReturnEmptyOptional(){
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<MovieResponse> result = movieService.findMovieById(99L);

        assertThat(result).isEmpty();
        verify(movieRepository).findById(99L);
    }
    @Test
    void registerMovie_shouldSaveAndReturnMovieResponse(){
        when(movieRepository.save(any(Movie.class))).thenReturn(testMovie);

        MovieResponse result = movieService.registerMovie(createRequest);

        assertThat(result.getTitle()).isEqualTo("Inception");
        verify(movieRepository).save(any(Movie.class));
    }
    @Test
    void deleteMovie_whenExists_shouldDeleteAndReturnTrue(){
        when(movieRepository.existsById(1L)).thenReturn(true);
        doNothing().when(movieRepository).deleteById(1L);

        Boolean result = movieService.deleteMovie(1L);

        assertThat(result).isTrue();
        verify(movieRepository).deleteById(1L);
    }
    @Test
    void deleteMovie_whenNotExists_shouldReturnFalse(){
        when(movieRepository.existsById(99L)).thenReturn(false);

        Boolean result = movieService.deleteMovie(99L);

        assertThat(result).isFalse();
        verify(movieRepository, never()).deleteById(99L);
    }
}

