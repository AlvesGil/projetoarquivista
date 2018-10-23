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
 * A Locatario.
 */
@Entity
@Table(name = "locatario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Locatario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imovel")
    private String imovel;

    @Column(name = "nome")
    private String nome;

    @Column(name = "jhi_end")
    private String end;

    @Column(name = "idt")
    private String idt;

    @Column(name = "cpf")
    private String cpf;

    @ManyToMany(mappedBy = "locatarios")
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

    public String getImovel() {
        return imovel;
    }

    public Locatario imovel(String imovel) {
        this.imovel = imovel;
        return this;
    }

    public void setImovel(String imovel) {
        this.imovel = imovel;
    }

    public String getNome() {
        return nome;
    }

    public Locatario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEnd() {
        return end;
    }

    public Locatario end(String end) {
        this.end = end;
        return this;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getIdt() {
        return idt;
    }

    public Locatario idt(String idt) {
        this.idt = idt;
        return this;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

    public String getCpf() {
        return cpf;
    }

    public Locatario cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Set<Arquivista> getArquivistas() {
        return arquivistas;
    }

    public Locatario arquivistas(Set<Arquivista> arquivistas) {
        this.arquivistas = arquivistas;
        return this;
    }

    public Locatario addArquivista(Arquivista arquivista) {
        this.arquivistas.add(arquivista);
        arquivista.getLocatarios().add(this);
        return this;
    }

    public Locatario removeArquivista(Arquivista arquivista) {
        this.arquivistas.remove(arquivista);
        arquivista.getLocatarios().remove(this);
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
        Locatario locatario = (Locatario) o;
        if (locatario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locatario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Locatario{" +
            "id=" + getId() +
            ", imovel='" + getImovel() + "'" +
            ", nome='" + getNome() + "'" +
            ", end='" + getEnd() + "'" +
            ", idt='" + getIdt() + "'" +
            ", cpf='" + getCpf() + "'" +
            "}";
    }
}
