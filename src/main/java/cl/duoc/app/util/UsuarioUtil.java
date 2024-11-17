package cl.duoc.app.util;

import org.springframework.stereotype.Component;

@Component
public class UsuarioUtil {

    private UsuarioUtil() {}

    public static boolean isEmptyOrNull(String string) {
        return null == string || string.isEmpty();
    }
}
