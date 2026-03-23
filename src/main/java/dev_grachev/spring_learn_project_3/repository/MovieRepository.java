package dev_grachev.spring_learn_project_3.repository;

import dev_grachev.spring_learn_project_3.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
