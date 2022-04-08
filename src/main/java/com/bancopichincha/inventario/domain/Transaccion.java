package com.bancopichincha.inventario.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transaccion.
 */
@Entity
@Table(name = "transaccion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "hora", nullable = false)
    private Instant hora;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JsonIgnoreProperties(value = { "transaccions" }, allowSetters = true)
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties(value = { "transaccions" }, allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transaccion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Transaccion fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Instant getHora() {
        return this.hora;
    }

    public Transaccion hora(Instant hora) {
        this.setHora(hora);
        return this;
    }

    public void setHora(Instant hora) {
        this.hora = hora;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public Transaccion cantidad(Integer cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Transaccion producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Transaccion cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaccion)) {
            return false;
        }
        return id != null && id.equals(((Transaccion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaccion{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", hora='" + getHora() + "'" +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
