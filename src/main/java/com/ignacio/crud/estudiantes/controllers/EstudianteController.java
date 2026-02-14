package com.ignacio.crud.estudiantes.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ignacio.crud.estudiantes.dto.EstudianteDTO;
import com.ignacio.crud.estudiantes.dto.EstudianteResponseDTO;
import com.ignacio.crud.estudiantes.dto.EstudiantePatchDTO;
import com.ignacio.crud.estudiantes.services.EstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> crearEstudiante(@Valid @RequestBody EstudianteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.crearEstudiante(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> obtenerEstudiantePorId(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteService.obtenerEstudiantePorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<EstudianteResponseDTO>> buscarEstudiante(
        @RequestParam(required = false) String nombre,
        @PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(estudianteService.buscar(nombre, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> actualizarEstudiante(@PathVariable Long id, @Valid @RequestBody EstudianteDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(estudianteService.actualizarEstudiante(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> actualizarEstudianteParcial(
            @PathVariable Long id, 
            @Valid @RequestBody EstudiantePatchDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(estudianteService.actualizarEstudianteParcial(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        estudianteService.eliminarEstudiante(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<EstudianteResponseDTO>> listarEstudiantes(
            @PageableDefault(size = 10, sort = "nombre")
            Pageable pageable) {

        return ResponseEntity.ok(estudianteService.listarEstudiantes(pageable));
    }
}

