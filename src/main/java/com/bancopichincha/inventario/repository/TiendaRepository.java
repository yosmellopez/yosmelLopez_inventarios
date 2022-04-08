package com.bancopichincha.inventario.repository;

import com.bancopichincha.inventario.domain.Tienda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tienda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {}
