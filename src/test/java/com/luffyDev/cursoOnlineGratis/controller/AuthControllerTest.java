package com.luffyDev.cursoOnlineGratis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luffyDev.cursoOnlineGratis.dto.RegisterRequest;
import com.luffyDev.cursoOnlineGratis.dto.LoginRequest;
import com.luffyDev.cursoOnlineGratis.model.Rol;
import com.luffyDev.cursoOnlineGratis.repository.RolRepository;
import com.luffyDev.cursoOnlineGratis.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        rolRepository.save(new Rol(null, Rol.NombreRol.ESTUDIANTE, "Estudiante", Rol.Estado.ACTIVO, null));
    }

    @Test
    void registroUsuarioExitoso() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setEmail("juan@email.com");
        request.setPassword("Password123");
        request.setRol("ESTUDIANTE");

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.email").value("juan@email.com"));
    }

    @Test
    void registroUsuarioEmailDuplicado() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setEmail("juan@email.com");
        request.setPassword("Password123");
        request.setRol("ESTUDIANTE");

        // Primer registro
        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        // Segundo registro con el mismo email
        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("El email ya está registrado"));
    }

    @Test
    void loginExitoso() throws Exception {
        // Registrar usuario primero
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setEmail("juan@email.com");
        request.setPassword("Password123");
        request.setRol("ESTUDIANTE");
        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("juan@email.com");
        loginRequest.setPassword("Password123");
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.email").value("juan@email.com"));
    }

    @Test
    void loginCredencialesIncorrectas() throws Exception {
        // Registrar usuario primero
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setEmail("juan@email.com");
        request.setPassword("Password123");
        request.setRol("ESTUDIANTE");
        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Login con contraseña incorrecta
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("juan@email.com");
        loginRequest.setPassword("WrongPassword");
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Credenciales inválidas"));
    }
}
