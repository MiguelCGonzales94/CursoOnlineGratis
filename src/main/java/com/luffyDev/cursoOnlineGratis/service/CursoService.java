package com.luffyDev.cursoOnlineGratis.service;

import com.luffyDev.cursoOnlineGratis.dto.CursoRequest;
import com.luffyDev.cursoOnlineGratis.dto.CursoResponse;
import com.luffyDev.cursoOnlineGratis.model.Categoria;
import com.luffyDev.cursoOnlineGratis.model.Curso;
import com.luffyDev.cursoOnlineGratis.model.Usuario;
import com.luffyDev.cursoOnlineGratis.repository.CategoriaRepository;
import com.luffyDev.cursoOnlineGratis.repository.CursoRepository;
import com.luffyDev.cursoOnlineGratis.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public CursoService(CursoRepository cursoRepository, UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Curso crearCurso(CursoRequest cursoRequest) {
        if (cursoRepository.existsByTitulo(cursoRequest.getTitulo())) {
            throw new RuntimeException("Ya existe un curso con ese título");
        }

        Usuario docente = usuarioRepository.findById(cursoRequest.getDocenteId())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        Curso curso = new Curso();
        curso.setTitulo(cursoRequest.getTitulo());
        curso.setDescripcion(cursoRequest.getDescripcion());
        curso.setNivel(cursoRequest.getNivel());
        curso.setDocente(docente);
        curso.setPrecio(cursoRequest.getPrecio());
        curso.setDuracionHoras(cursoRequest.getDuracionHoras());
        curso.setImagenUrl(cursoRequest.getImagenUrl());
        curso.setEsGratis(cursoRequest.getEsGratis());

        if (cursoRequest.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(cursoRequest.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            curso.setCategoria(categoria);
        }

        return cursoRepository.save(curso);
    }

    public List<CursoResponse> obtenerTodosLosCursos() {
        return cursoRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<CursoResponse> obtenerCursosPublicados() {
        return cursoRepository.findByEstado(Curso.Estado.PUBLICADO).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<CursoResponse> obtenerCursosGratis() {
        return cursoRepository.findByEsGratis(true).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public Optional<Curso> obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public List<Curso> obtenerCursosPorDocente(Long docenteId) {
        return cursoRepository.findByDocenteId(docenteId);
    }

    @Transactional
    public Curso publicarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        curso.setEstado(Curso.Estado.PUBLICADO);
        return cursoRepository.save(curso);
    }

    @Transactional
    public void eliminarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        cursoRepository.delete(curso);
    }

    private CursoResponse convertirAResponse(Curso curso) {
        CursoResponse response = new CursoResponse();
        response.setId(curso.getId());
        response.setTitulo(curso.getTitulo());
        response.setDescripcion(curso.getDescripcion());
        response.setNivel(curso.getNivel());
        response.setEstado(curso.getEstado());
        response.setDocenteId(curso.getDocente().getId());
        response.setDocenteNombre(curso.getDocente().getNombreCompleto());
        response.setCategoriaId(curso.getCategoria() != null ? curso.getCategoria().getId() : null);
        response.setCategoriaNombre(curso.getCategoria() != null ? curso.getCategoria().getNombre() : null);
        response.setPrecio(curso.getPrecio());
        response.setDuracionHoras(curso.getDuracionHoras());
        response.setFechaCreacion(curso.getFechaCreacion());
        response.setFechaActualizacion(curso.getFechaActualizacion());
        response.setImagenUrl(curso.getImagenUrl());
        response.setEsGratis(curso.getEsGratis());

        Long totalEstudiantes = cursoRepository.countEstudiantesByCursoId(curso.getId());
        response.setTotalEstudiantes(totalEstudiantes != null ? totalEstudiantes.intValue() : 0);

        return response;
    }
}