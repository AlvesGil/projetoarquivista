package br.ufpa.arquivista.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Login.
 */
@Entity
@Table(name = "login")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "senha")
    private Integer senha;

    @OneToOne    @JoinColumn(unique = true)
    private Arquivista arquivista;

    @OneToOne    @JoinColumn(unique = true)
    private Locador locador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public Login usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getSenha() {
        return senha;
    }

    public Login senha(Integer senha) {
        this.senha = senha;
        return this;
    }

    public void setSenha(Integer senha) {
        this.senha = senha;
    }

    public Arquivista getArquivista() {
        return arquivista;
    }

    public Login arquivista(Arquivista arquivista) {
        this.arquivista = arquivista;
        return this;
    }

    public void setArquivista(Arquivista arquivista) {
        this.arquivista = arquivista;
    }

    public Locador getLocador() {
        return locador;
    }

    public Login locador(Locador locador) {
        this.locador = locador;
        return this;
    }

    public void setLocador(Locador locador) {
        this.locador = locador;
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
        Login login = (Login) o;
        if (login.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), login.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Login{" +
            "id=" + getId() +
            ", usuario='" + getUsuario() + "'" +
            ", senha=" + getSenha() +
            "}";
    }
}
