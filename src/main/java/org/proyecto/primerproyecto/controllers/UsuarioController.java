package org.proyecto.primerproyecto.controllers;

import org.proyecto.primerproyecto.models.Usuario;
import org.proyecto.primerproyecto.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(this.usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        Optional<Usuario>usuario = this.usuarioService.getUserById(id);

        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/create/{username}/{password}/{nombre}/{email}/{edad}")
    public void createUser(@PathVariable String username,
                           @PathVariable String password,
                           @PathVariable String nombre,
                           @PathVariable String email,
                           @PathVariable int edad) {

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setEdad(edad);

        this.usuarioService.createUser(usuario);
    }

    @GetMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        this.usuarioService.delete(id);
    }


}
