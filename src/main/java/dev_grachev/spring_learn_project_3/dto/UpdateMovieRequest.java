package dev_grachev.spring_learn_project_3.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateMovieRequest {
    @NotBlank(message = "title required!")
    private String title;

    @NotBlank(message = "director required!")
    private String director;

    @Min(1800)
    @Max(2030)
    @NotNull(message = "releaseYear required!")
    private Integer releaseYear;

    private String genre;
}
