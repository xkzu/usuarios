package cl.duoc.app;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class AppTest {

    @Test
    void testMain() {
        String[] args = {"--spring.profiles.active=test"};

        try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
            App.main(args);
            mockedSpringApplication.verify(() -> SpringApplication.run(App.class, args));
        }
    }

    @Test
    void testMainNoArgs() {
        String[] emptyArgs = {};
        try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
            App.main(emptyArgs);
            mockedSpringApplication.verify(() -> SpringApplication.run(App.class, emptyArgs));
        }
    }
}
