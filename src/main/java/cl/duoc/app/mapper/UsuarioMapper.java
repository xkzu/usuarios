package cl.duoc.app.mapper;

import cl.duoc.app.dto.UsuarioDTO;
import cl.duoc.app.model.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getPassword(),
                usuario.getEmail(),
                usuario.isAdmin()
        );
    }

    public static Usuario toEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setAdmin(usuarioDTO.isAdmin());
        usuario.setPassword(usuarioDTO.getPassword());
        return usuario;
    }
}
