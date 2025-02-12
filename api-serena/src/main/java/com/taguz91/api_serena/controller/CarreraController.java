package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.criteria.CriteriaHelper;
import com.taguz91.api_serena.api.criteria.builder.CarreraSpecificationBuilder;
import com.taguz91.api_serena.api.criteria.builder.TeacherSpecificationBuilder;
import com.taguz91.api_serena.api.request.CarreraRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.Carrera;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("api/v1/carrera")
public class CarreraController {

    @Autowired
    private CarreraRepository carreraRepository;

    // Obtener lista paginada de Carreras
    @GetMapping("")
    public ResponseEntity<PageResponse<Carrera>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        CriteriaHelper<Carrera> criteriaHelper = new CriteriaHelper<>(
                new CarreraSpecificationBuilder()
        );
        Specification<Carrera> spec = criteriaHelper.build(search);
        Pageable pageable = PageRequest.of(page, size);
        Page<Carrera> carreras = carreraRepository.findAll(spec, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(carreras));
    }

    // Crear una nueva Carrera
    @PostMapping("")
    public ResponseEntity<Carrera> create(@Valid @RequestBody CarreraRequest request) {
        Carrera carrera = request.toCarrera();
        Carrera saved = carreraRepository.save(carrera);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    // Actualizar una Carrera existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Carrera> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody CarreraRequest request
    ) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La carrera no existe"));

        carrera.setName(request.getName());
        carrera.setDescription(request.getDescription());

        carreraRepository.save(carrera);

        return ResponseEntity.status(HttpStatus.OK)
                .body(carrera);
    }

    // Obtener una Carrera por ID
    @GetMapping("/{id}")
    public ResponseEntity<Carrera> one(
            @PathVariable(value = "id") String id
    ) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La carrera no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(carrera);
    }

    // Eliminar una Carrera por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        carreraRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Carrera eliminada con éxito"));
    }

    // Obtener todas las Carreras en formato de opciones
    @GetMapping("/all/options")
    public ResponseEntity<List<OptionResponse>> options() {
        List<OptionResponse> options = carreraRepository.findAllOptions();

        return ResponseEntity.status(HttpStatus.OK)
                .body(options);
    }
}
