package cl.duoc.app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan.perez@example.com");
        usuario.setPassword("secret");
        usuario.setAdmin(true);

        assertEquals(1L, usuario.getId());
        assertEquals("Juan Perez", usuario.getNombre());
        assertEquals("juan.perez@example.com", usuario.getEmail());
        assertEquals("secret", usuario.getPassword());
        assertTrue(usuario.isAdmin());

        String toString = usuario.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Juan Perez"));
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        Usuario usuario = new Usuario(
                2L,
                "Maria Lopez",
                "maria.lopez@example.com",
                "password123",
                false
        );

        assertEquals(2L, usuario.getId());
        assertEquals("Maria Lopez", usuario.getNombre());
        assertEquals("maria.lopez@example.com", usuario.getEmail());
        assertEquals("password123", usuario.getPassword());
        assertFalse(usuario.isAdmin());

        Usuario usuario2 = new Usuario(
                2L,
                "Maria Lopez",
                "maria.lopez@example.com",
                "password123",
                false
        );

        assertEquals(usuario, usuario2);
        assertEquals(usuario.hashCode(), usuario2.hashCode());

        Usuario usuario3 = new Usuario(
                3L,
                "Carlos Gomez",
                "carlos.gomez@example.com",
                "pw",
                true
        );
        assertNotEquals(usuario, usuario3);
    }

}
