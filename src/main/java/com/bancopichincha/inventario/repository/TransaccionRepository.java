package com.bancopichincha.inventario.repository;

import com.bancopichincha.inventario.domain.Transaccion;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Transaccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    @Query("SELECT t FROM Transaccion t GROUP BY t.fecha")
    List<Transaccion> findTransaccionGroupByFecha();
}
