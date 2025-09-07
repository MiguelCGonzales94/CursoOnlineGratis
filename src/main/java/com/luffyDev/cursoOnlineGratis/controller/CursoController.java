package com.luffyDev.cursoOnlineGratis.controller;

import com.luffyDev.cursoOnlineGratis.dto.CursoRequest;
import com.luffyDev.cursoOnlineGratis.dto.CursoResponse;
import com.luffyDev.cursoOnlineGratis.model.Curso;
import com.luffyDev.cursoOnlineGratis.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<?> crearCurso(@Valid @RequestBody CursoRequest request) {
        try {
            Curso curso = cursoService.crearCurso(request);
            return ResponseEntity.ok(curso);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> obtenerTodosLosCursos() {
        return ResponseEntity.ok(cursoService.obtenerTodosLosCursos());
    }

    @GetMapping("/publicados")
    public ResponseEntity<List<CursoResponse>> obtenerCursosPublicados() {
        return ResponseEntity.ok(cursoService.obtenerCursosPublicados());
    }

    @GetMapping("/gratis")
    public ResponseEntity<List<CursoResponse>> obtenerCursosGratis() {
        return ResponseEntity.ok(cursoService.obtenerCursosGratis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCurso(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.obtenerCursoPorId(id);
        return curso.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<Curso>> obtenerCursosPorDocente(@PathVariable Long docenteId) {
        return ResponseEntity.ok(cursoService.obtenerCursosPorDocente(docenteId));
    }

    @PutMapping("/{id}/publicar")
    public ResponseEntity<?> publicarCurso(@PathVariable Long id) {
        try {
            Curso curso = cursoService.publicarCurso(id);
            return ResponseEntity.ok(curso);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id) {
        try {
            cursoService.eliminarCurso(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al eliminar curso"));
        }
    }
}