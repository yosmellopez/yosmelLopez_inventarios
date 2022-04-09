package com.bancopichincha.inventario.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

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
    @Column(name = "identificacion", nullable = false)
    private String identificacion;

    @NotNull
    @Column(name = "foto", nullable = false)
    private String foto;

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "producto", "cliente" }, allowSetters = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Transaccion> transaccions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Cliente nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return this.identificacion;
    }

    public Cliente identificacion(String identificacion) {
        this.setIdentificacion(identificacion);
        return this;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getFoto() {
        return this.foto;
    }

    public Cliente foto(String foto) {
        this.setFoto(foto);
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Set<Transaccion> getTransaccions() {
        return this.transaccions;
    }

    public void setTransaccions(Set<Transaccion> transaccions) {
        if (this.transaccions != null) {
            this.transaccions.forEach(i -> i.setCliente(null));
        }
        if (transaccions != null) {
            transaccions.forEach(i -> i.setCliente(this));
        }
        this.transaccions = transaccions;
    }

    public Cliente transaccions(Set<Transaccion> transaccions) {
        this.setTransaccions(transaccions);
        return this;
    }

    public Cliente addTransaccion(Transaccion transaccion) {
        this.transaccions.add(transaccion);
        transaccion.setCliente(this);
        return this;
    }

    public Cliente removeTransaccion(Transaccion transaccion) {
        this.transaccions.remove(transaccion);
        transaccion.setCliente(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", identificacion='" + getIdentificacion() + "'" +
            ", foto='" + getFoto() + "'" +
            "}";
    }
}
