package cl.duoc.app.service;


import cl.duoc.app.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario);

    void delete(Long id);

    Optional<Usuario> login(String email, String password);

    void passwordRecovery(String email, String password);
}
