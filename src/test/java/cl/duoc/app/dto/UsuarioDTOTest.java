package cl.duoc.app.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDTOTest {

    @Test
    void testGettersAndSetters() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("John Doe");
        usuarioDTO.setPassword("password123");
        usuarioDTO.setEmail("johndoe@example.com");
        usuarioDTO.setAdmin(true);

        assertEquals(1L, usuarioDTO.getId(), "El getter para id no devuelve el valor esperado");
        assertEquals("John Doe", usuarioDTO.getNombre(), "El getter para nombre no devuelve el valor esperado");
        assertEquals("password123", usuarioDTO.getPassword(), "El getter para password no devuelve el valor esperado");
        assertEquals("johndoe@example.com", usuarioDTO.getEmail(), "El getter para email no devuelve el valor esperado");
        assertTrue(usuarioDTO.isAdmin(), "El getter para admin no devuelve el valor esperado");
    }

    @Test
    void testAllArgsConstructor() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(2L, "Jane Doe", "pass456", "janedoe@example.com", false);

        assertEquals(2L, usuarioDTO.getId(), "El constructor con argumentos no asigna correctamente el id");
        assertEquals("Jane Doe", usuarioDTO.getNombre(), "El constructor con argumentos no asigna correctamente el nombre");
        assertEquals("pass456", usuarioDTO.getPassword(), "El constructor con argumentos no asigna correctamente el password");
        assertEquals("janedoe@example.com", usuarioDTO.getEmail(), "El constructor con argumentos no asigna correctamente el email");
        assertFalse(usuarioDTO.isAdmin(), "El constructor con argumentos no asigna correctamente el valor de admin");
    }

    @Test
    void testNoArgsConstructor() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        assertNull(usuarioDTO.getId(), "El constructor sin argumentos debería inicializar id como null");
        assertNull(usuarioDTO.getNombre(), "El constructor sin argumentos debería inicializar nombre como null");
        assertNull(usuarioDTO.getPassword(), "El constructor sin argumentos debería inicializar password como null");
        assertNull(usuarioDTO.getEmail(), "El constructor sin argumentos debería inicializar email como null");
        assertFalse(usuarioDTO.isAdmin(), "El constructor sin argumentos debería inicializar admin como false");
    }

    @Test
    void testToString() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "John Doe", "password123", "johndoe@example.com", true);
        String toString = usuarioDTO.toString();

        assertNotNull(toString, "El método toString no debería devolver null");
        assertTrue(toString.contains("id=1"), "El toString debería contener el id");
        assertTrue(toString.contains("nombre=John Doe"), "El toString debería contener el nombre");
        assertTrue(toString.contains("password=password123"), "El toString debería contener el password");
        assertTrue(toString.contains("email=johndoe@example.com"), "El toString debería contener el email");
        assertTrue(toString.contains("admin=true"), "El toString debería contener el valor de admin como true");
        assertFalse(toString.contains("null"), "El toString no debería contener valores null");
    }
}
