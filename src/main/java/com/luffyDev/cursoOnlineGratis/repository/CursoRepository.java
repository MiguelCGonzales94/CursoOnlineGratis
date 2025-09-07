package com.luffyDev.cursoOnlineGratis.repository;

import com.luffyDev.cursoOnlineGratis.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByTitulo(String titulo);
    boolean existsByTitulo(String titulo);
    
    List<Curso> findByEstado(Curso.Estado estado);
    List<Curso> findByDocenteId(Long docenteId);
    List<Curso> findByCategoriaId(Long categoriaId);
    List<Curso> findByEsGratis(Boolean esGratis);
    
    @Query("SELECT c FROM Curso c WHERE c.nivel = :nivel")
    List<Curso> findByNivel(@Param("nivel") Curso.Nivel nivel);
    
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.curso.id = :cursoId AND i.estado = 'ACTIVA'")
    Long countEstudiantesByCursoId(@Param("cursoId") Long cursoId);
}