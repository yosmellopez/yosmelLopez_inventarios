package com.bancopichincha.inventario.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tienda.
 */
@Entity
@Table(name = "tienda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tienda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @ManyToMany
    @JoinTable(name = "tienda_producto",
            joinColumns = @JoinColumn(name = "tienda_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(value = {"transaccions"}, allowSetters = true)
    private Set<Producto> productos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tienda id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Tienda nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Tienda codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void addAllProducto(List<Producto> productos) {
        this.productos.addAll(productos);
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tienda)) {
            return false;
        }
        return id != null && id.equals(((Tienda) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tienda{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + "'" +
                ", codigo='" + getCodigo() + "'" +
                "}";
    }

}
