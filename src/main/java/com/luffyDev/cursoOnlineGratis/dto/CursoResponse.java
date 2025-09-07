package com.luffyDev.cursoOnlineGratis.dto;

import com.luffyDev.cursoOnlineGratis.model.Curso;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CursoResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private Curso.Nivel nivel;
    private Curso.Estado estado;
    private Long docenteId;
    private String docenteNombre;
    private Long categoriaId;
    private String categoriaNombre;
    private BigDecimal precio;
    private Integer duracionHoras;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String imagenUrl;
    private Boolean esGratis;
    private Integer totalEstudiantes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Curso.Nivel getNivel() { return nivel; }
    public void setNivel(Curso.Nivel nivel) { this.nivel = nivel; }
    public Curso.Estado getEstado() { return estado; }
    public void setEstado(Curso.Estado estado) { this.estado = estado; }
    public Long getDocenteId() { return docenteId; }
    public void setDocenteId(Long docenteId) { this.docenteId = docenteId; }
    public String getDocenteNombre() { return docenteNombre; }
    public void setDocenteNombre(String docenteNombre) { this.docenteNombre = docenteNombre; }
    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    public String getCategoriaNombre() { return categoriaNombre; }
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getDuracionHoras() { return duracionHoras; }
    public void setDuracionHoras(Integer duracionHoras) { this.duracionHoras = duracionHoras; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public Boolean getEsGratis() { return esGratis; }
    public void setEsGratis(Boolean esGratis) { this.esGratis = esGratis; }
    public Integer getTotalEstudiantes() { return totalEstudiantes; }
    public void setTotalEstudiantes(Integer totalEstudiantes) { this.totalEstudiantes = totalEstudiantes; }
}