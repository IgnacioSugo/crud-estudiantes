package com.ignacio.crud.estudiantes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String nombre;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private Integer edad;

    @NotNull
    private LocalDate fechaIngreso;

}
