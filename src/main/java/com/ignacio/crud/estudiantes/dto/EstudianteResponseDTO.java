package com.ignacio.crud.estudiantes.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstudianteResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private Integer edad;
    private LocalDate fechaIngreso;
}
