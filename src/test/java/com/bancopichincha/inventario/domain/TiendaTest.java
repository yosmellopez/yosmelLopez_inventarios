package com.bancopichincha.inventario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bancopichincha.inventario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TiendaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tienda.class);
        Tienda tienda1 = new Tienda();
        tienda1.setId(1L);
        Tienda tienda2 = new Tienda();
        tienda2.setId(tienda1.getId());
        assertThat(tienda1).isEqualTo(tienda2);
        tienda2.setId(2L);
        assertThat(tienda1).isNotEqualTo(tienda2);
        tienda1.setId(null);
        assertThat(tienda1).isNotEqualTo(tienda2);
    }
}
