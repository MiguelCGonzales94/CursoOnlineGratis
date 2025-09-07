package com.luffyDev.cursoOnlineGratis.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false, length = 50)
    private NombreRol nombre;
    
    @Column(length = 200)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Estado estado = Estado.ACTIVO;
    
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Usuario> usuarios = new HashSet<>();
    
    public enum NombreRol {
        ADMIN, DOCENTE, ESTUDIANTE
    }
    
    public enum Estado {
        ACTIVO, INACTIVO
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setNombre(NombreRol nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public NombreRol getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public Estado getEstado() {
        return estado;
    }
}