package com.luffyDev.cursoOnlineGratis.dto;

import com.luffyDev.cursoOnlineGratis.model.Curso;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CursoRequest {
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 5, max = 200, message = "El título debe tener entre 5 y 200 caracteres")
    private String titulo;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    private String descripcion;
    
    @NotNull(message = "El nivel es obligatorio")
    private Curso.Nivel nivel;
    
    @NotNull(message = "El ID del docente es obligatorio")
    private Long docenteId;
    
    private Long categoriaId;
    
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    private BigDecimal precio = BigDecimal.ZERO;
    
    @Min(value = 1, message = "La duración debe ser al menos 1 hora")
    @Max(value = 1000, message = "La duración no puede exceder 1000 horas")
    private Integer duracionHoras;
    
    @Size(max = 500, message = "La URL de la imagen no puede exceder 500 caracteres")
    private String imagenUrl;
    
    private Boolean esGratis = true;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Curso.Nivel getNivel() { return nivel; }
    public void setNivel(Curso.Nivel nivel) { this.nivel = nivel; }
    public Long getDocenteId() { return docenteId; }
    public void setDocenteId(Long docenteId) { this.docenteId = docenteId; }
    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getDuracionHoras() { return duracionHoras; }
    public void setDuracionHoras(Integer duracionHoras) { this.duracionHoras = duracionHoras; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public Boolean getEsGratis() { return esGratis; }
    public void setEsGratis(Boolean esGratis) { this.esGratis = esGratis; }
}