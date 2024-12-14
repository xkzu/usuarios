package cl.duoc.app.controller;

import cl.duoc.app.dto.UsuarioDTO;
import cl.duoc.app.mapper.UsuarioMapper;
import cl.duoc.app.model.Usuario;
import cl.duoc.app.service.UsuarioService;
import cl.duoc.app.util.UsuarioUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Optional<Usuario> usuario = usuarioService.login(usuarioDTO.getEmail(), usuarioDTO.getPassword());
            return usuario.map(value -> ResponseEntity.ok(UsuarioMapper.toDTO(value)))
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTO>> listar() {
        try {
            List<UsuarioDTO> usuariosDTO = usuarioService.findAll()
                    .stream()
                    .map(UsuarioMapper::toDTO) // Convertir la entidad a DTO
                    .toList();
            return ResponseEntity.ok(usuariosDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Usuario> usuario = usuarioService.findById(id);
            return usuario.map(value -> ResponseEntity.ok(UsuarioMapper.toDTO(value)))
                    .orElseGet(() -> ResponseEntity.ok().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<UsuarioDTO> agregar(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            if (UsuarioUtil.isEmptyOrNull(usuarioDTO.getNombre())
                    || UsuarioUtil.isEmptyOrNull(usuarioDTO.getEmail())
                    || UsuarioUtil.isEmptyOrNull(usuarioDTO.getPassword())) {
                return ResponseEntity.badRequest().build();
            }
            Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
            Usuario savedUsuario = usuarioService.save(usuario);
            return ResponseEntity.ok(UsuarioMapper.toDTO(savedUsuario));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            log.info("id: {}, usuarioDTO: {}", id, usuarioDTO);
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build();
            }
            Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
            usuario.setId(id);
            log.info("usuario: {}", usuario);
            Usuario updatedUsuario = usuarioService.save(usuario);
            return ResponseEntity.ok(UsuarioMapper.toDTO(updatedUsuario));
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build();
            }
            usuarioService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/recuperar/password/{email}/{password}")
    public ResponseEntity<UsuarioDTO> recuperarPassword(@PathVariable String email, @PathVariable String password) {
        log.info("email: {}, password: {}", email, password);
        try {
            if (UsuarioUtil.isEmptyOrNull(email) || UsuarioUtil.isEmptyOrNull(password)) {
                return ResponseEntity.badRequest().build();
            }
            usuarioService.passwordRecovery(email, password);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
