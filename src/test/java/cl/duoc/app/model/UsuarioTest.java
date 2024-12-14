package cl.duoc.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testGettersAndSetters() {
        Usuario usuario = new Usuario();

        usuario.setId(1L);
        usuario.setNombre("John Doe");
        usuario.setEmail("johndoe@example.com");
        usuario.setPassword("password123");
        usuario.setAdmin(true);

        assertEquals(1L, usuario.getId(), "El getter para id no devuelve el valor esperado");
        assertEquals("John Doe", usuario.getNombre(), "El getter para nombre no devuelve el valor esperado");
        assertEquals("johndoe@example.com", usuario.getEmail(), "El getter para email no devuelve el valor esperado");
        assertEquals("password123", usuario.getPassword(), "El getter para password no devuelve el valor esperado");
        assertTrue(usuario.isAdmin(), "El getter para admin no devuelve el valor esperado");
    }

    @Test
    void testAllArgsConstructor() {
        Usuario usuario = new Usuario(2L, "Jane Doe", "janedoe@example.com", "pass456", false);

        assertEquals(2L, usuario.getId(), "El constructor con argumentos no asigna correctamente el id");
        assertEquals("Jane Doe", usuario.getNombre(), "El constructor con argumentos no asigna correctamente el nombre");
        assertEquals("janedoe@example.com", usuario.getEmail(), "El constructor con argumentos no asigna correctamente el email");
        assertEquals("pass456", usuario.getPassword(), "El constructor con argumentos no asigna correctamente el password");
        assertFalse(usuario.isAdmin(), "El constructor con argumentos no asigna correctamente el valor de admin");
    }

    @Test
    void testNoArgsConstructor() {
        Usuario usuario = new Usuario();

        assertNull(usuario.getId(), "El constructor sin argumentos debería inicializar id como null");
        assertNull(usuario.getNombre(), "El constructor sin argumentos debería inicializar nombre como null");
        assertNull(usuario.getEmail(), "El constructor sin argumentos debería inicializar email como null");
        assertNull(usuario.getPassword(), "El constructor sin argumentos debería inicializar password como null");
        assertFalse(usuario.isAdmin(), "El constructor sin argumentos debería inicializar admin como false");
    }

    @Test
    void testToString() {
        Usuario usuario = new Usuario(1L, "John Doe", "johndoe@example.com", "password123", true);
        String toString = usuario.toString();

        assertNotNull(toString, "El método toString no debería devolver null");
        assertTrue(toString.contains("id=1"), "El toString debería contener el id");
        assertTrue(toString.contains("nombre=John Doe"), "El toString debería contener el nombre");
        assertTrue(toString.contains("email=johndoe@example.com"), "El toString debería contener el email");
        assertTrue(toString.contains("password=password123"), "El toString debería contener el password");
        assertTrue(toString.contains("admin=true"), "El toString debería contener el valor de admin como true");
    }
}
