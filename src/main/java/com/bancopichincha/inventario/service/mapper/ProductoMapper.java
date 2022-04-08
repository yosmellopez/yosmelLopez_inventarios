package com.bancopichincha.inventario.service.mapper;

import com.bancopichincha.inventario.domain.Producto;
import com.bancopichincha.inventario.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {}
