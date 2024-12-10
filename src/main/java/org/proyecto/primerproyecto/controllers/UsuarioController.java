package org.proyecto.primerproyecto.controllers;

import org.proyecto.primerproyecto.models.Usuario;
import org.proyecto.primerproyecto.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
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

    @PostMapping("/create")
    public void createUser(@RequestBody Usuario usuario) {
        this.usuarioService.createUser(usuario);
    }

    @GetMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        this.usuarioService.delete(id);
    }

    @GetMapping("/status")
    public Map<String, String> checkStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "conexión establecida");
        return response;
    }
}
