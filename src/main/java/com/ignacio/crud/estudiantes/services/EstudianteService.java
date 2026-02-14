package com.ignacio.crud.estudiantes.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ignacio.crud.estudiantes.dto.EstudianteDTO;
import com.ignacio.crud.estudiantes.dto.EstudianteResponseDTO;
import com.ignacio.crud.estudiantes.dto.EstudiantePatchDTO;
import com.ignacio.crud.estudiantes.entity.Estudiante;
import com.ignacio.crud.estudiantes.exceptions.EstudianteNoEncontradoException;
import com.ignacio.crud.estudiantes.exceptions.EmailYaExisteException;
import com.ignacio.crud.estudiantes.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteResponseDTO crearEstudiante(EstudianteDTO dto) {
        Estudiante estudiante = dtoToEntity(dto);
        Estudiante guardado = estudianteRepository.save(estudiante);
        return entityToDto(guardado);
    }
    
    public EstudianteResponseDTO obtenerEstudiantePorId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
            .orElseThrow(() -> new EstudianteNoEncontradoException("Estudiante no encontrado con id: " + id));
        log.debug("Estudiante encontrado con el id {}", id);
        return entityToDto(estudiante);
    }

    public Page<EstudianteResponseDTO> buscar(String nombre, Pageable pageable) {
        Page<Estudiante> page;
        if (nombre == null || nombre.isBlank()) {
            page = estudianteRepository.findAll(pageable);
        } else {
            page = estudianteRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        }
        return page.map(this::entityToDto);
    }

    @Transactional
    public EstudianteResponseDTO actualizarEstudiante(Long id, EstudianteDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(id)
            .orElseThrow(() -> new EstudianteNoEncontradoException("Estudiante no encontrado con el id: " + id));
        log.debug("Estudiante encontrado con el id {}", id);

        if (!estudiante.getEmail().equals(dto.getEmail()) &&
             estudianteRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new EmailYaExisteException("El email ya está registrado");
            
        }
        
        estudiante.setNombre(dto.getNombre());
        estudiante.setEmail(dto.getEmail());
        estudiante.setEdad(dto.getEdad());
        estudiante.setFechaIngreso(dto.getFechaIngreso());

        return entityToDto(estudiante);
    }

    @Transactional
    public EstudianteResponseDTO actualizarEstudianteParcial(Long id, EstudiantePatchDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(id)
            .orElseThrow(() -> new EstudianteNoEncontradoException("Estudiante no encontradocon el id: " + id));
        
        if (dto.getNombre() != null) {
            estudiante.setNombre(dto.getNombre());
        }
        if (dto.getEmail() != null) {
            if (!estudiante.getEmail().equals(dto.getEmail()) &&
                estudianteRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new EmailYaExisteException("El email ya está registrado");
            }
            estudiante.setEmail(dto.getEmail());
        }
        if (dto.getEdad() != null) {
            estudiante.setEdad(dto.getEdad());
        }
        if (dto.getFechaIngreso() != null) {
            estudiante.setFechaIngreso(dto.getFechaIngreso());
        }
        return entityToDto(estudiante);
    }

    public Estudiante dtoToEntity(EstudianteDTO dto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        estudiante.setEmail(dto.getEmail());
        estudiante.setEdad(dto.getEdad());
        estudiante.setFechaIngreso(dto.getFechaIngreso());
        return estudiante;
    }

    public EstudianteResponseDTO entityToDto(Estudiante estudiante) {
        return new EstudianteResponseDTO(
            estudiante.getId(),
            estudiante.getNombre(),
            estudiante.getEmail(),
            estudiante.getEdad(),
            estudiante.getFechaIngreso()
        );
    }

    @Transactional
    public void eliminarEstudiante(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
            .orElseThrow(() -> new EstudianteNoEncontradoException("Estudiante no encontrado con el id: " + id));
        estudianteRepository.delete(estudiante);
    }

    public Page<EstudianteResponseDTO> listarEstudiantes(Pageable pageable) {
        Page<Estudiante> page = estudianteRepository.findAll(pageable);
        return page.map(this::entityToDto);
    }
}
