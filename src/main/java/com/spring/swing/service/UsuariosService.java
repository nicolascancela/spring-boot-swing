package com.spring.swing.service;

import com.spring.swing.domain.Usuario;
import com.spring.swing.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuariosService {
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerUsuarios(){return usuarioRepository.findAll();}

    public void cargarListaPredefinida(){
        List<Usuario> usuarios = List.of(
                crearUsuario("Pepe", "Perez"),
                crearUsuario("Jorge", "Aguirre"),
                crearUsuario("Luis", "Alvarez"),
                crearUsuario("Marcos", "Islas"),
                crearUsuario("Patricia", "Mendez")
        );
        usuarioRepository.saveAll(usuarios);
    }

    public void borrarTodosLosUsuarios(){usuarioRepository.deleteAll();    }

    public void crearNuevoUsuario(String nombreYApellido) {
        if (nombreYApellido.trim().length()==0) throw new IllegalArgumentException("El nombre y apellido no pueden ser vacios");
        if (nombreYApellido.split(" ").length != 2) throw new IllegalArgumentException("Nombre y apellido incorrectos.");
        String[] nombreApellido = nombreYApellido.split(" ");
        usuarioRepository.save(crearUsuario(nombreApellido[0], nombreApellido[1]));
    }

    private Usuario crearUsuario(String nombre, String apellido) {
        return Usuario.builder()
                .nombre(nombre)
                .apellido(apellido)
                .build();
    }
}
