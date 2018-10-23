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
 * A Pagamento.
 */
@Entity
@Table(name = "pagamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "condominio")
    private String condominio;

    @Column(name = "iptu")
    private String iptu;

    @Column(name = "luz")
    private String luz;

    @Column(name = "agua")
    private String agua;

    @Column(name = "diversos")
    private String diversos;

    @ManyToOne
    @JsonIgnoreProperties("pagamentos")
    private Locador locador;

    @ManyToMany(mappedBy = "pagamentos")
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

    public String getCondominio() {
        return condominio;
    }

    public Pagamento condominio(String condominio) {
        this.condominio = condominio;
        return this;
    }

    public void setCondominio(String condominio) {
        this.condominio = condominio;
    }

    public String getIptu() {
        return iptu;
    }

    public Pagamento iptu(String iptu) {
        this.iptu = iptu;
        return this;
    }

    public void setIptu(String iptu) {
        this.iptu = iptu;
    }

    public String getLuz() {
        return luz;
    }

    public Pagamento luz(String luz) {
        this.luz = luz;
        return this;
    }

    public void setLuz(String luz) {
        this.luz = luz;
    }

    public String getAgua() {
        return agua;
    }

    public Pagamento agua(String agua) {
        this.agua = agua;
        return this;
    }

    public void setAgua(String agua) {
        this.agua = agua;
    }

    public String getDiversos() {
        return diversos;
    }

    public Pagamento diversos(String diversos) {
        this.diversos = diversos;
        return this;
    }

    public void setDiversos(String diversos) {
        this.diversos = diversos;
    }

    public Locador getLocador() {
        return locador;
    }

    public Pagamento locador(Locador locador) {
        this.locador = locador;
        return this;
    }

    public void setLocador(Locador locador) {
        this.locador = locador;
    }

    public Set<Arquivista> getArquivistas() {
        return arquivistas;
    }

    public Pagamento arquivistas(Set<Arquivista> arquivistas) {
        this.arquivistas = arquivistas;
        return this;
    }

    public Pagamento addArquivista(Arquivista arquivista) {
        this.arquivistas.add(arquivista);
        arquivista.getPagamentos().add(this);
        return this;
    }

    public Pagamento removeArquivista(Arquivista arquivista) {
        this.arquivistas.remove(arquivista);
        arquivista.getPagamentos().remove(this);
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
        Pagamento pagamento = (Pagamento) o;
        if (pagamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pagamento{" +
            "id=" + getId() +
            ", condominio='" + getCondominio() + "'" +
            ", iptu='" + getIptu() + "'" +
            ", luz='" + getLuz() + "'" +
            ", agua='" + getAgua() + "'" +
            ", diversos='" + getDiversos() + "'" +
            "}";
    }
}
