package br.ufpa.arquivista.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Imovel.
 */
@Entity
@Table(name = "imovel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Imovel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "jhi_end")
    private String end;

    @Column(name = "contrato")
    private String contrato;

    @Column(name = "ficha")
    private String ficha;

    @Column(name = "oficios")
    private String oficios;

    @Column(name = "aditivo")
    private String aditivo;

    @ManyToOne
    @JsonIgnoreProperties("imovels")
    private Vistoria vistoria;

    @OneToMany(mappedBy = "imovel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Locatario> locatarios = new HashSet<>();
    @OneToMany(mappedBy = "imovel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Locador> locadors = new HashSet<>();
    @ManyToMany(mappedBy = "imovels")
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

    public String getNome() {
        return nome;
    }

    public Imovel nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEnd() {
        return end;
    }

    public Imovel end(String end) {
        this.end = end;
        return this;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getContrato() {
        return contrato;
    }

    public Imovel contrato(String contrato) {
        this.contrato = contrato;
        return this;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getFicha() {
        return ficha;
    }

    public Imovel ficha(String ficha) {
        this.ficha = ficha;
        return this;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public String getOficios() {
        return oficios;
    }

    public Imovel oficios(String oficios) {
        this.oficios = oficios;
        return this;
    }

    public void setOficios(String oficios) {
        this.oficios = oficios;
    }

    public String getAditivo() {
        return aditivo;
    }

    public Imovel aditivo(String aditivo) {
        this.aditivo = aditivo;
        return this;
    }

    public void setAditivo(String aditivo) {
        this.aditivo = aditivo;
    }

    public Vistoria getVistoria() {
        return vistoria;
    }

    public Imovel vistoria(Vistoria vistoria) {
        this.vistoria = vistoria;
        return this;
    }

    public void setVistoria(Vistoria vistoria) {
        this.vistoria = vistoria;
    }

    public Set<Locatario> getLocatarios() {
        return locatarios;
    }

    public Imovel locatarios(Set<Locatario> locatarios) {
        this.locatarios = locatarios;
        return this;
    }

    public Imovel addLocatario(Locatario locatario) {
        this.locatarios.add(locatario);
        locatario.setImovel(this);
        return this;
    }

    public Imovel removeLocatario(Locatario locatario) {
        this.locatarios.remove(locatario);
        locatario.setImovel(null);
        return this;
    }

    public void setLocatarios(Set<Locatario> locatarios) {
        this.locatarios = locatarios;
    }

    public Set<Locador> getLocadors() {
        return locadors;
    }

    public Imovel locadors(Set<Locador> locadors) {
        this.locadors = locadors;
        return this;
    }

    public Imovel addLocador(Locador locador) {
        this.locadors.add(locador);
        locador.setImovel(this);
        return this;
    }

    public Imovel removeLocador(Locador locador) {
        this.locadors.remove(locador);
        locador.setImovel(null);
        return this;
    }

    public void setLocadors(Set<Locador> locadors) {
        this.locadors = locadors;
    }

    public Set<Arquivista> getArquivistas() {
        return arquivistas;
    }

    public Imovel arquivistas(Set<Arquivista> arquivistas) {
        this.arquivistas = arquivistas;
        return this;
    }

    public Imovel addArquivista(Arquivista arquivista) {
        this.arquivistas.add(arquivista);
        arquivista.getImovels().add(this);
        return this;
    }

    public Imovel removeArquivista(Arquivista arquivista) {
        this.arquivistas.remove(arquivista);
        arquivista.getImovels().remove(this);
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
        Imovel imovel = (Imovel) o;
        if (imovel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imovel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Imovel{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", end='" + getEnd() + "'" +
            ", contrato='" + getContrato() + "'" +
            ", ficha='" + getFicha() + "'" +
            ", oficios='" + getOficios() + "'" +
            ", aditivo='" + getAditivo() + "'" +
            "}";
    }
}
