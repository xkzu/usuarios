package cl.duoc.app.mapper;

import cl.duoc.app.dto.UsuarioDTO;
import cl.duoc.app.model.Usuario;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    @Test
    void testToDTO() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("John Doe");
        usuario.setEmail("john@example.com");
        usuario.setPassword("password");
        usuario.setAdmin(true);

        UsuarioDTO dto = UsuarioMapper.toDTO(usuario);

        assertEquals(1L, dto.getId());
        assertEquals("John Doe", dto.getNombre());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals("password", dto.getPassword());
        assertTrue(dto.isAdmin());
    }

    @Test
    void testToEntity() {

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(2L);
        dto.setNombre("Jane Doe");
        dto.setEmail("jane@example.com");
        dto.setPassword("pass123");
        dto.setAdmin(false);

        Usuario usuario = UsuarioMapper.toEntity(dto);

        assertEquals(2L, usuario.getId());
        assertEquals("Jane Doe", usuario.getNombre());
        assertEquals("jane@example.com", usuario.getEmail());
        assertEquals("pass123", usuario.getPassword());
        assertFalse(usuario.isAdmin());
    }

    @Test
    void testPrivateConstructor() throws Exception {

        Constructor<UsuarioMapper> constructor = UsuarioMapper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        UsuarioMapper instance = constructor.newInstance();
        assertNotNull(instance);
    }
}
