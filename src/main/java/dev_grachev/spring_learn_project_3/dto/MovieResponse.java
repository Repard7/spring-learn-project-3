package dev_grachev.spring_learn_project_3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieResponse {
    private Long id;
    private String title;
    private String director;
    private Integer releaseYear;
    private String genre;
}
