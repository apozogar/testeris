package com.everis.testeris.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.everis.testeris.domain.enumeration.Estado;

/**
 * A Paso.
 */
@Entity
@Table(name = "paso")
public class Paso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "num_paso")
    private String numPaso;

    @Column(name = "resultado_esperado")
    private String resultadoEsperado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @ManyToOne
    private Caso caso;

    @OneToMany(mappedBy = "paso")
    @JsonIgnore
    private Set<Imagen> imagens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Paso nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Paso descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumPaso() {
        return numPaso;
    }

    public Paso numPaso(String numPaso) {
        this.numPaso = numPaso;
        return this;
    }

    public void setNumPaso(String numPaso) {
        this.numPaso = numPaso;
    }

    public String getResultadoEsperado() {
        return resultadoEsperado;
    }

    public Paso resultadoEsperado(String resultadoEsperado) {
        this.resultadoEsperado = resultadoEsperado;
        return this;
    }

    public void setResultadoEsperado(String resultadoEsperado) {
        this.resultadoEsperado = resultadoEsperado;
    }

    public Estado getEstado() {
        return estado;
    }

    public Paso estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Caso getCaso() {
        return caso;
    }

    public Paso caso(Caso caso) {
        this.caso = caso;
        return this;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    public Set<Imagen> getImagens() {
        return imagens;
    }

    public Paso imagens(Set<Imagen> imagens) {
        this.imagens = imagens;
        return this;
    }

    public Paso addImagen(Imagen imagen) {
        this.imagens.add(imagen);
        imagen.setPaso(this);
        return this;
    }

    public Paso removeImagen(Imagen imagen) {
        this.imagens.remove(imagen);
        imagen.setPaso(null);
        return this;
    }

    public void setImagens(Set<Imagen> imagens) {
        this.imagens = imagens;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Paso paso = (Paso) o;
        if (paso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Paso{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", numPaso='" + getNumPaso() + "'" +
            ", resultadoEsperado='" + getResultadoEsperado() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
