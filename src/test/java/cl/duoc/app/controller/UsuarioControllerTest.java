package cl.duoc.app.controller;

import cl.duoc.app.dto.UsuarioDTO;
import cl.duoc.app.mapper.UsuarioMapper;
import cl.duoc.app.model.Usuario;
import cl.duoc.app.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper;
    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("John Doe");
        usuario.setEmail("john@example.com");
        usuario.setPassword("secret");
        usuario.setAdmin(true);

        usuarioDTO = UsuarioMapper.toDTO(usuario);
    }

    @Test
    void testLoginSuccess() throws Exception {
        when(usuarioService.login("john@example.com", "secret")).thenReturn(Optional.of(usuario));

        UsuarioDTO requestDto = new UsuarioDTO();
        requestDto.setEmail("john@example.com");
        requestDto.setPassword("secret");

        mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(usuarioService, times(1)).login("john@example.com", "secret");
    }

    @Test
    void testLoginUnauthorized() throws Exception {
        when(usuarioService.login("nope@example.com", "wrong")).thenReturn(Optional.empty());

        UsuarioDTO requestDto = new UsuarioDTO();
        requestDto.setEmail("nope@example.com");
        requestDto.setPassword("wrong");

        mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isUnauthorized());

        verify(usuarioService, times(1)).login("nope@example.com", "wrong");
    }

    @Test
    void testListar() throws Exception {
        Usuario u2 = new Usuario();
        u2.setId(2L);
        u2.setNombre("Jane Doe");
        u2.setEmail("jane@example.com");
        u2.setPassword("pass");
        u2.setAdmin(false);

        List<Usuario> usuarios = Arrays.asList(usuario, u2);
        when(usuarioService.findAll()).thenReturn(usuarios);

        mockMvc.perform(get("/usuarios/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"));

        verify(usuarioService, times(1)).findAll();
    }

    @Test
    void testBuscarFound() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/buscar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(usuarioService, times(1)).findById(1L);
    }

    @Test
    void testBuscarNotFound() throws Exception {
        when(usuarioService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/buscar/999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(usuarioService, times(1)).findById(999L);
    }

    @Test
    void testBuscarBadRequest() throws Exception {
        mockMvc.perform(get("/usuarios/buscar/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAgregarSuccess() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("New User");
        dto.setEmail("newuser@example.com");
        dto.setPassword("newpass");
        dto.setAdmin(false);

        Usuario newUsuario = UsuarioMapper.toEntity(dto);
        newUsuario.setId(5L); // Simulamos el ID asignado por la BD

        when(usuarioService.save(ArgumentMatchers.any(Usuario.class))).thenReturn(newUsuario);

        mockMvc.perform(post("/usuarios/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.email").value("newuser@example.com"));

        verify(usuarioService, times(1)).save(any(Usuario.class));
    }

    @Test
    void testAgregarBadRequest() throws Exception {
        // Falta email, por ejemplo
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("No Email User");
        dto.setPassword("noemail");

        mockMvc.perform(post("/usuarios/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(usuarioService, never()).save(any(Usuario.class));
    }

    @Test
    void testActualizarSuccess() throws Exception {
        UsuarioDTO updateDTO = new UsuarioDTO();
        updateDTO.setNombre("Updated Name");
        updateDTO.setEmail("updated@example.com");
        updateDTO.setPassword("updatedPass");
        updateDTO.setAdmin(true);

        Usuario updatedUsuario = UsuarioMapper.toEntity(updateDTO);
        updatedUsuario.setId(1L);

        when(usuarioService.save(any(Usuario.class))).thenReturn(updatedUsuario);

        mockMvc.perform(put("/usuarios/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@example.com"));

        verify(usuarioService, times(1)).save(any(Usuario.class));
    }

    @Test
    void testActualizarBadRequest() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Should Fail");

        mockMvc.perform(put("/usuarios/actualizar/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(usuarioService, never()).save(any(Usuario.class));
    }

    @Test
    void testEliminarSuccess() throws Exception {
        mockMvc.perform(delete("/usuarios/eliminar/1"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).delete(1L);
    }

    @Test
    void testEliminarBadRequest() throws Exception {
        mockMvc.perform(delete("/usuarios/eliminar/-1"))
                .andExpect(status().isBadRequest());

        verify(usuarioService, never()).delete(anyLong());
    }

    @Test
    void testRecuperarPasswordSuccess() throws Exception {
        mockMvc.perform(put("/usuarios/recuperar/password/test@example.com/newPass"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).passwordRecovery("test@example.com", "newPass");
    }


}
