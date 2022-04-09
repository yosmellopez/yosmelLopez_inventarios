package com.bancopichincha.inventario.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "cod", nullable = false)
    private String cod;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Long stock;

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "producto", "cliente" }, allowSetters = true)
    private Set<Transaccion> transaccions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCod() {
        return this.cod;
    }

    public Producto cod(String cod) {
        this.setCod(cod);
        return this;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getName() {
        return this.name;
    }

    public Producto name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public Producto price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStock() {
        return this.stock;
    }

    public Producto stock(Long stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Set<Transaccion> getTransaccions() {
        return this.transaccions;
    }

    public void setTransaccions(Set<Transaccion> transaccions) {
        if (this.transaccions != null) {
            this.transaccions.forEach(i -> i.setProducto(null));
        }
        if (transaccions != null) {
            transaccions.forEach(i -> i.setProducto(this));
        }
        this.transaccions = transaccions;
    }

    public Producto transaccions(Set<Transaccion> transaccions) {
        this.setTransaccions(transaccions);
        return this;
    }

    public Producto addTransaccion(Transaccion transaccion) {
        this.transaccions.add(transaccion);
        transaccion.setProducto(this);
        return this;
    }

    public Producto removeTransaccion(Transaccion transaccion) {
        this.transaccions.remove(transaccion);
        transaccion.setProducto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", cod='" + getCod() + "'" +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", stock=" + getStock() +
            "}";
    }
}
