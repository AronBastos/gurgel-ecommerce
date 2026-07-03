package com.gurgel.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Verifica que o contexto Spring sobe corretamente (todas as beans e o
 * mapeamento JPA são válidos).
 */
@SpringBootTest
@ActiveProfiles("test")
class GurgelEcommerceApplicationTests {

    @Test
    void contextLoads() {
    }
}
