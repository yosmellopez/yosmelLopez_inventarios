package com.bancopichincha.inventario.repository;

import com.bancopichincha.inventario.domain.Cliente;
import com.bancopichincha.inventario.domain.Transaccion;
import com.bancopichincha.inventario.service.dto.TransaccionFecha;
import com.bancopichincha.inventario.service.dto.TransaccionInfo;
import com.bancopichincha.inventario.service.dto.TransaccionMonto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Transaccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    @Query("SELECT new com.bancopichincha.inventario.service.dto.TransaccionFecha(t.fecha, t.tienda, COUNT(t.fecha)) FROM Transaccion t GROUP BY t.fecha, t.tienda")
    List<TransaccionFecha> findTransaccionGroupByFecha();

    @Query("SELECT new com.bancopichincha.inventario.service.dto.TransaccionMonto(t.tienda, t.producto, SUM(t.cantidad)) FROM Transaccion t GROUP BY t.tienda, t.producto")
    List<TransaccionMonto> findTransaccionGroupByMontos();

    List<TransaccionInfo> findTransaccionByClienteAndFechaBetween(Cliente cliente, LocalDate startDate, LocalDate endDate);
}
