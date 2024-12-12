package org.proyecto.primerproyecto.services;


import org.proyecto.primerproyecto.models.Usuario;
import org.proyecto.primerproyecto.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    public Optional<Usuario> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public void createUser(Usuario user) {
        this.userRepository.save(user);
    }

    public Usuario update(Usuario user) {
        return this.userRepository.save(user);
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    public Optional<Usuario> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public boolean authnticate(String username, String password) {
        Optional<Usuario> usuario = this.userRepository.findByUsernameAndPassword(username, password);
        return usuario.isPresent();
    }

    public String authWithPassword(String username, String password) {
        Optional<Usuario> usuarioProv = this.userRepository.findByUsername(username);

        if (usuarioProv.isEmpty())
            return "Usuario no existe";

        Usuario usuario = usuarioProv.get();
        if (usuario.getPassword().equals(password))
            return "Usuario existe";

        else
            return "Contraseña incorrecta";
    }
}
