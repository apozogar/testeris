package com.everis.testeris.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Generated by JHipster IDE plugin
 */
@ApiModel(description = "Generated by JHipster IDE plugin")
@Entity
@Table(name = "proyecto")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "proyecto")
    @JsonIgnore
    private Set<Caso> casos = new HashSet<>();

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

    public Proyecto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public Proyecto url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Caso> getCasos() {
        return casos;
    }

    public Proyecto casos(Set<Caso> casos) {
        this.casos = casos;
        return this;
    }

    public Proyecto addCasos(Caso caso) {
        this.casos.add(caso);
        caso.setProyecto(this);
        return this;
    }

    public Proyecto removeCasos(Caso caso) {
        this.casos.remove(caso);
        caso.setProyecto(null);
        return this;
    }

    public void setCasos(Set<Caso> casos) {
        this.casos = casos;
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
        Proyecto proyecto = (Proyecto) o;
        if (proyecto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proyecto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
