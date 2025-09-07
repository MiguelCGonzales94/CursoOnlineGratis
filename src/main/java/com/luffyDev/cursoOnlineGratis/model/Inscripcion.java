package com.luffyDev.cursoOnlineGratis.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscripciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Usuario estudiante;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    
    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Estado estado = Estado.ACTIVA;
    
    @Column(name = "progreso_porcentaje")
    private Integer progresoPorcentaje = 0;
    
    public enum Estado {
        ACTIVA, COMPLETADA, CANCELADA
    }
}