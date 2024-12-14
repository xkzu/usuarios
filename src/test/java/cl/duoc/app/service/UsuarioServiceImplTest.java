package cl.duoc.app.service;

import cl.duoc.app.model.Usuario;
import cl.duoc.app.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioServiceImpl service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testFindAll() {
        List<Usuario> usuariosMock = Arrays.asList(new Usuario(), new Usuario());
        when(repository.findAll()).thenReturn(usuariosMock);

        List<Usuario> result = service.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindByIdFound() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<Usuario> result = service.findById(2L);
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(2L);
    }

    @Test
    void testSave() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        when(repository.save(usuario)).thenReturn(usuario);

        Usuario result = service.save(usuario);
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(repository, times(1)).save(usuario);
    }

    @Test
    void testDelete() {
        service.delete(3L);
        verify(repository, times(1)).deleteById(3L);
    }

    @Test
    void testLoginSuccess() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("secret");
        when(repository.findByEmailAndPassword("test@example.com", "secret")).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = service.login("test@example.com", "secret");
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(repository, times(1)).findByEmailAndPassword("test@example.com", "secret");
    }

    @Test
    void testLoginFail() {
        when(repository.findByEmailAndPassword("nope@example.com", "wrong")).thenReturn(Optional.empty());

        Optional<Usuario> result = service.login("nope@example.com", "wrong");
        assertFalse(result.isPresent());
        verify(repository, times(1)).findByEmailAndPassword("nope@example.com", "wrong");
    }

    @Test
    void testPasswordRecovery() {
        service.passwordRecovery("test@example.com", "newPass");
        verify(repository, times(1)).updatePasswordByEmail("test@example.com", "newPass");
    }
}
