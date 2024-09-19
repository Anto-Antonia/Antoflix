package com.example.Antoflix.dto.request.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGenreRequest {

    @NotBlank(message = "The genre must not be blank")
    private String name;
}
