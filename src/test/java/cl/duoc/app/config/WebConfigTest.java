package cl.duoc.app.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

import static org.mockito.Mockito.*;

class WebConfigTest {

    @Test
    void testAddCorsMappings() {

        CorsRegistry registry = Mockito.mock(CorsRegistry.class);

        CorsRegistration registration = Mockito.mock(CorsRegistration.class);

        when(registry.addMapping("/**")).thenReturn(registration);

        when(registration.allowedOrigins("http://localhost:4200")).thenReturn(registration);
        when(registration.allowedMethods("GET","POST","PUT","DELETE","OPTIONS")).thenReturn(registration);
        when(registration.allowedHeaders("*")).thenReturn(registration);
        when(registration.allowCredentials(true)).thenReturn(registration);

        WebConfig webConfig = new WebConfig();
        webConfig.addCorsMappings(registry);

        verify(registry, times(1)).addMapping("/**");

        verify(registration, times(1)).allowedOrigins("http://localhost:4200");
        verify(registration, times(1)).allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
        verify(registration, times(1)).allowedHeaders("*");
        verify(registration, times(1)).allowCredentials(true);
    }
}
