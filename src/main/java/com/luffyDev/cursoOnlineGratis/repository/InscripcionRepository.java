package com.luffyDev.cursoOnlineGratis.repository;

import com.luffyDev.cursoOnlineGratis.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    Optional<Inscripcion> findByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);
    List<Inscripcion> findByEstudianteId(Long estudianteId);
    List<Inscripcion> findByCursoId(Long cursoId);
    
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.curso.id = :cursoId AND i.estado = 'ACTIVA'")
    Long countByCursoId(@Param("cursoId") Long cursoId);
    
    @Query("SELECT i FROM Inscripcion i WHERE i.estudiante.id = :estudianteId AND i.estado = 'ACTIVA'")
    List<Inscripcion> findCursosActivosByEstudiante(@Param("estudianteId") Long estudianteId);
}