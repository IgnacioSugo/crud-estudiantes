package com.ignacio.crud.estudiantes.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstudiantePatchDTO {
    private String nombre;
    private String email;
    private Integer edad;
    private LocalDate fechaIngreso;
}
