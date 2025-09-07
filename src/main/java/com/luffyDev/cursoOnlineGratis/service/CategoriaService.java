package com.luffyDev.cursoOnlineGratis.service;

import com.luffyDev.cursoOnlineGratis.dto.CategoriaRequest;
import com.luffyDev.cursoOnlineGratis.model.Categoria;
import com.luffyDev.cursoOnlineGratis.repository.CategoriaRepository;
import com.luffyDev.cursoOnlineGratis.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    
    @Transactional
    public Categoria crearCategoria(CategoriaRequest request) {
        if (categoriaRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        categoria.setImagenUrl(request.getImagenUrl());
        categoria.setEstado(request.getEstado());
        
        return categoriaRepository.save(categoria);
    }
    
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }
    
    public List<Categoria> obtenerCategoriasActivas() {
        return categoriaRepository.findByEstado(Categoria.Estado.ACTIVA);
    }
    
    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }
    
    public Optional<Categoria> obtenerCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }
    
    @Transactional
    public Categoria actualizarCategoria(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        
        // Verificar si el nombre ya existe en otra categoría
        if (!categoria.getNombre().equals(request.getNombre()) && 
            categoriaRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        categoria.setImagenUrl(request.getImagenUrl());
        categoria.setEstado(request.getEstado());
        
        return categoriaRepository.save(categoria);
    }
    
    @Transactional
    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }
    
    @Transactional
    public Categoria activarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        categoria.setEstado(Categoria.Estado.ACTIVA);
        return categoriaRepository.save(categoria);
    }
    
    @Transactional
    public Categoria desactivarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        categoria.setEstado(Categoria.Estado.INACTIVA);
        return categoriaRepository.save(categoria);
    }
    
    public long contarCategoriasActivas() {
        return categoriaRepository.findByEstado(Categoria.Estado.ACTIVA).size();
    }
    
    public long contarTotalCategorias() {
        return categoriaRepository.count();
    }
}