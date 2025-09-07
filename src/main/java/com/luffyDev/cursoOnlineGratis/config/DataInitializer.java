package com.luffyDev.cursoOnlineGratis.config;

import com.luffyDev.cursoOnlineGratis.model.Rol;
import com.luffyDev.cursoOnlineGratis.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RolRepository rolRepository;

    public DataInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        crearRolesIniciales();
        System.out.println("✅ Inicialización de datos completada");
    }

    private void crearRolesIniciales() {
        List<String> roles = Arrays.asList("ADMIN", "DOCENTE", "ESTUDIANTE");

        for (String nombreRol : roles) {
            Rol.NombreRol nombre = Rol.NombreRol.valueOf(nombreRol);

            // Verificar si el rol ya existe
            boolean existe = rolRepository.findByNombre(nombre).isPresent();

            if (!existe) {
                Rol rol = new Rol();
                rol.setNombre(nombre);
                rol.setDescripcion(getDescripcionRol(nombreRol));
                rol.setEstado(Rol.Estado.ACTIVO);
                rolRepository.save(rol);
                System.out.println("✅ Rol creado: " + nombreRol);
            }
        }
    }

    private String getDescripcionRol(String nombreRol) {
        switch (nombreRol) {
            case "ADMIN":
                return "Administrador del sistema";
            case "DOCENTE":
                return "Docente/instructor de cursos";
            case "ESTUDIANTE":
                return "Estudiante de cursos";
            default:
                return "Rol del sistema";
        }
    }
}