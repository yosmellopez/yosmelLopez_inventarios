package com.bancopichincha.inventario.repository;

import com.bancopichincha.inventario.domain.Tienda;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tienda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {

    @NotNull
    @EntityGraph(attributePaths = {"productos"})
    List<Tienda> findAll();
}
