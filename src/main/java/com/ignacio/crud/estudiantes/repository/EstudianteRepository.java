package com.ignacio.crud.estudiantes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ignacio.crud.estudiantes.entity.Estudiante;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long>{
    List<Estudiante> findByNombre(String nombre);
    Page<Estudiante> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    List<Estudiante> findByEdad(Integer edad);
    Optional<Estudiante> findByEmail(String email);
    List<Estudiante> findByFechaIngreso(LocalDate fechaIngreso);
    List<Estudiante> findByNombreAndFechaIngreso(String nombre, LocalDate fechaIngreso);

}
