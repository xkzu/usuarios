package cl.duoc.app.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UsuarioUtilTest {

    @Test
    void testIsEmptyOrNullWithNull() {
        assertTrue(UsuarioUtil.isEmptyOrNull(null), "Debería retornar true si el string es null");
    }

    @Test
    void testIsEmptyOrNullWithEmptyString() {
        assertTrue(UsuarioUtil.isEmptyOrNull(""), "Debería retornar true si el string está vacío");
    }

    @Test
    void testIsEmptyOrNullWithNonEmptyString() {
        assertFalse(UsuarioUtil.isEmptyOrNull("hello"), "Debería retornar false si el string no está vacío");
    }
}
