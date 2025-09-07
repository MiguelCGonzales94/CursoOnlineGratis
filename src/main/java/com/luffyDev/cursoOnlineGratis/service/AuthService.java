package com.luffyDev.cursoOnlineGratis.service;

import com.luffyDev.cursoOnlineGratis.dto.LoginRequest;
import com.luffyDev.cursoOnlineGratis.dto.RegisterRequest;
import com.luffyDev.cursoOnlineGratis.exception.ResourceNotFoundException;
import com.luffyDev.cursoOnlineGratis.model.Rol;
import com.luffyDev.cursoOnlineGratis.model.Usuario;
import com.luffyDev.cursoOnlineGratis.repository.RolRepository;
import com.luffyDev.cursoOnlineGratis.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Map<String, Object> registrarUsuario(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        Rol.NombreRol rolNombre = Rol.NombreRol.valueOf(request.getRol().toUpperCase());
        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + request.getRol()));

        usuario.agregarRol(rol);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Map<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", usuarioGuardado.getEmail());
        // Puedes agregar más campos si lo necesitas

        Map<String, Object> response = new HashMap<>();
        response.put("usuario", usuarioMap);
        response.put("mensaje", "Usuario registrado exitosamente");

        return response;
    }

    public Map<String, Object> login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        Map<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", usuario.getEmail());
        // Puedes agregar más campos si lo necesitas

        Map<String, Object> response = new HashMap<>();
        response.put("usuario", usuarioMap);
        response.put("mensaje", "Login exitoso");

        return response;
    }
}