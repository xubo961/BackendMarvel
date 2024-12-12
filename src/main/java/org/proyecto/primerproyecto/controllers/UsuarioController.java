package org.proyecto.primerproyecto.controllers;

import org.proyecto.primerproyecto.models.Usuario;
import org.proyecto.primerproyecto.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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


    /*
    *
    *credenciales = {"username": Un Nombre, "password": Una contraseña}
    *
    **/
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credenciales) {

        String username = credenciales.get("username");
        String password = credenciales.get("password");

        boolean isAuth = this.usuarioService.authnticate(username, password);
        Map<String, Object> response = new HashMap<>();

        if (isAuth) {
            response.put("message", "Login exitoso");
            response.put("login", true);
            return ResponseEntity.ok(response);
        }
        else {
            response.put("message", "Credenciales no válidos");
            response.put("login", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/login/v2")
    public ResponseEntity<Map<String, Object>> loginV2(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        String result = this.usuarioService.authWithPassword(username, password);
        Map<String, Object> response = new HashMap<>();

        if (result.equals("Usuario existe")) {
            response.put("message", result);
            response.put("loged", true);
            return ResponseEntity.ok(response); //Código de estado 200 -> entra en el next
        }
        else if (result.equals("Contraseña incorrecta")) {
            response.put("message", result);
            response.put("loged", false);
            //Código de estado 401 -> Esto entra en el error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        else {
            response.put("message", result);
            response.put("loged", false);
            //Código de estado 404 -> Esto entra en el error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
