package br.ufpa.arquivista.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Comprovante.
 */
@Entity
@Table(name = "comprovante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comprovante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mes")
    private String mes;

    @Column(name = "ano")
    private Integer ano;

    @ManyToMany(mappedBy = "comprovantes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Arquivista> arquivistas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMes() {
        return mes;
    }

    public Comprovante mes(String mes) {
        this.mes = mes;
        return this;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public Comprovante ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Set<Arquivista> getArquivistas() {
        return arquivistas;
    }

    public Comprovante arquivistas(Set<Arquivista> arquivistas) {
        this.arquivistas = arquivistas;
        return this;
    }

    public Comprovante addArquivista(Arquivista arquivista) {
        this.arquivistas.add(arquivista);
        arquivista.getComprovantes().add(this);
        return this;
    }

    public Comprovante removeArquivista(Arquivista arquivista) {
        this.arquivistas.remove(arquivista);
        arquivista.getComprovantes().remove(this);
        return this;
    }

    public void setArquivistas(Set<Arquivista> arquivistas) {
        this.arquivistas = arquivistas;
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
        Comprovante comprovante = (Comprovante) o;
        if (comprovante.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comprovante.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comprovante{" +
            "id=" + getId() +
            ", mes='" + getMes() + "'" +
            ", ano=" + getAno() +
            "}";
    }
}
