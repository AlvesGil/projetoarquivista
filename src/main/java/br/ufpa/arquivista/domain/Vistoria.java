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
 * A Vistoria.
 */
@Entity
@Table(name = "vistoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vistoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "documentos")
    private String documentos;

    @Column(name = "imagens")
    private String imagens;

    @OneToMany(mappedBy = "vistoria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Imovel> imovels = new HashSet<>();
    @ManyToMany(mappedBy = "vistorias")
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

    public String getDocumentos() {
        return documentos;
    }

    public Vistoria documentos(String documentos) {
        this.documentos = documentos;
        return this;
    }

    public void setDocumentos(String documentos) {
        this.documentos = documentos;
    }

    public String getImagens() {
        return imagens;
    }

    public Vistoria imagens(String imagens) {
        this.imagens = imagens;
        return this;
    }

    public void setImagens(String imagens) {
        this.imagens = imagens;
    }

    public Set<Imovel> getImovels() {
        return imovels;
    }

    public Vistoria imovels(Set<Imovel> imovels) {
        this.imovels = imovels;
        return this;
    }

    public Vistoria addImovel(Imovel imovel) {
        this.imovels.add(imovel);
        imovel.setVistoria(this);
        return this;
    }

    public Vistoria removeImovel(Imovel imovel) {
        this.imovels.remove(imovel);
        imovel.setVistoria(null);
        return this;
    }

    public void setImovels(Set<Imovel> imovels) {
        this.imovels = imovels;
    }

    public Set<Arquivista> getArquivistas() {
        return arquivistas;
    }

    public Vistoria arquivistas(Set<Arquivista> arquivistas) {
        this.arquivistas = arquivistas;
        return this;
    }

    public Vistoria addArquivista(Arquivista arquivista) {
        this.arquivistas.add(arquivista);
        arquivista.getVistorias().add(this);
        return this;
    }

    public Vistoria removeArquivista(Arquivista arquivista) {
        this.arquivistas.remove(arquivista);
        arquivista.getVistorias().remove(this);
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
        Vistoria vistoria = (Vistoria) o;
        if (vistoria.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vistoria.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vistoria{" +
            "id=" + getId() +
            ", documentos='" + getDocumentos() + "'" +
            ", imagens='" + getImagens() + "'" +
            "}";
    }
}
