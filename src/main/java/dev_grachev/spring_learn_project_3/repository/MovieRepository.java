package dev_grachev.spring_learn_project_3.repository;

import dev_grachev.spring_learn_project_3.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByDirector(String director);
    List<Movie> findAllByGenre(String genre);
    int countByGenre(String genre);
}
