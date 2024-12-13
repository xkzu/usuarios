package cl.duoc.app.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UsuarioDTOTest {

    @Test
    void testNoArgsConstructorAndSetters() {

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("Juan");
        usuarioDTO.setPassword("secret");
        usuarioDTO.setEmail("juan@example.com");
        usuarioDTO.setAdmin(true);

        assertEquals(1L, usuarioDTO.getId());
        assertEquals("Juan", usuarioDTO.getNombre());
        assertEquals("secret", usuarioDTO.getPassword());
        assertEquals("juan@example.com", usuarioDTO.getEmail());
        assertTrue(usuarioDTO.isAdmin());

        String toStringResult = usuarioDTO.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("Juan"));
    }

    @Test
    void testAllArgsConstructorAndEqualsHashCode() {

        UsuarioDTO usuario1 = new UsuarioDTO(2L, "Maria", "pass123", "maria@example.com", false);

        assertEquals(2L, usuario1.getId());
        assertEquals("Maria", usuario1.getNombre());
        assertEquals("pass123", usuario1.getPassword());
        assertEquals("maria@example.com", usuario1.getEmail());
        assertFalse(usuario1.isAdmin());

        UsuarioDTO usuario2 = new UsuarioDTO(2L, "Maria", "pass123", "maria@example.com", false);

        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());

        UsuarioDTO usuario3 = new UsuarioDTO(3L, "Carlos", "pass789", "carlos@example.com", true);

        assertNotEquals(usuario1, usuario3);
    }
}
