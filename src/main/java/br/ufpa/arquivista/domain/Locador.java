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
 * A Locador.
 */
@Entity
@Table(name = "locador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Locador implements Serializable {

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

    @OneToMany(mappedBy = "locador")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pagamento> pagamentos = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("locadors")
    private Imovel imovel;

    @OneToOne(mappedBy = "locador")
    @JsonIgnore
    private Login login;

    @ManyToMany(mappedBy = "locadors")
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

    public Locador imovel(String imovel) {
        this.imovel = imovel;
        return this;
    }

    public void setImovel(String imovel) {
        this.imovel = imovel;
    }

    public String getNome() {
        return nome;
    }

    public Locador nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEnd() {
        return end;
    }

    public Locador end(String end) {
        this.end = end;
        return this;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getIdt() {
        return idt;
    }

    public Locador idt(String idt) {
        this.idt = idt;
        return this;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

    public String getCpf() {
        return cpf;
    }

    public Locador cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Set<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public Locador pagamentos(Set<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
        return this;
    }

    public Locador addPagamentos(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
        pagamento.setLocador(this);
        return this;
    }

    public Locador removePagamentos(Pagamento pagamento) {
        this.pagamentos.remove(pagamento);
        pagamento.setLocador(null);
        return this;
    }

    public void setPagamentos(Set<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public Locador imovel(Imovel imovel) {
        this.imovel = imovel;
        return this;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Login getLogin() {
        return login;
    }

    public Locador login(Login login) {
        this.login = login;
        return this;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Set<Arquivista> getArquivistas() {
        return arquivistas;
    }

    public Locador arquivistas(Set<Arquivista> arquivistas) {
        this.arquivistas = arquivistas;
        return this;
    }

    public Locador addArquivista(Arquivista arquivista) {
        this.arquivistas.add(arquivista);
        arquivista.getLocadors().add(this);
        return this;
    }

    public Locador removeArquivista(Arquivista arquivista) {
        this.arquivistas.remove(arquivista);
        arquivista.getLocadors().remove(this);
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
        Locador locador = (Locador) o;
        if (locador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Locador{" +
            "id=" + getId() +
            ", imovel='" + getImovel() + "'" +
            ", nome='" + getNome() + "'" +
            ", end='" + getEnd() + "'" +
            ", idt='" + getIdt() + "'" +
            ", cpf='" + getCpf() + "'" +
            "}";
    }
}
