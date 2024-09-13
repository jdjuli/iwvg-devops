package es.upm.miw.iwvg_devops.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static es.upm.miw.iwvg_devops.rest.SystemResource.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:test.properties")
class SystemResourceIT {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadBadge() {
        this.webTestClient
                .get().uri(SYSTEM + VERSION_BADGE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(byte[].class)
                .value(Assertions::assertNotNull)
                .value(svg -> assertTrue(new String(svg).startsWith("<svg")));
    }

    @Test
    void testReadInfo() {
        this.webTestClient
                .get().uri(SYSTEM + APP_INFO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(Assertions::assertNotNull)
                .value(body -> assertEquals(3, body.split("::").length));
    }

    @Test
    void testCurrentTime() {
        this.webTestClient
                .get().uri(SYSTEM + CURRENT_TIME)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(Assertions::assertNotNull)
                .value(body -> assertTrue(body.matches("\\{\"time\": +\"\\d{2}:\\d{2}:\\d{2}\"}")));
    }
}
