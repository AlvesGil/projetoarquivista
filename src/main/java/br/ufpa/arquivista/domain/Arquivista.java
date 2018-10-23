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
 * A Arquivista.
 */
@Entity
@Table(name = "arquivista")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Arquivista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cadlocador")
    private String cadlocador;

    @Column(name = "cadlocatario")
    private String cadlocatario;

    @Column(name = "cadimovel")
    private String cadimovel;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "arquivista_locador",
               joinColumns = @JoinColumn(name = "arquivistas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "locadors_id", referencedColumnName = "id"))
    private Set<Locador> locadors = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "arquivista_locatario",
               joinColumns = @JoinColumn(name = "arquivistas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "locatarios_id", referencedColumnName = "id"))
    private Set<Locatario> locatarios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "arquivista_imovel",
               joinColumns = @JoinColumn(name = "arquivistas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "imovels_id", referencedColumnName = "id"))
    private Set<Imovel> imovels = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "arquivista_pagamento",
               joinColumns = @JoinColumn(name = "arquivistas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "pagamentos_id", referencedColumnName = "id"))
    private Set<Pagamento> pagamentos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "arquivista_comprovante",
               joinColumns = @JoinColumn(name = "arquivistas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "comprovantes_id", referencedColumnName = "id"))
    private Set<Comprovante> comprovantes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "arquivista_vistoria",
               joinColumns = @JoinColumn(name = "arquivistas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "vistorias_id", referencedColumnName = "id"))
    private Set<Vistoria> vistorias = new HashSet<>();

    @OneToOne(mappedBy = "arquivista")
    @JsonIgnore
    private Login login;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCadlocador() {
        return cadlocador;
    }

    public Arquivista cadlocador(String cadlocador) {
        this.cadlocador = cadlocador;
        return this;
    }

    public void setCadlocador(String cadlocador) {
        this.cadlocador = cadlocador;
    }

    public String getCadlocatario() {
        return cadlocatario;
    }

    public Arquivista cadlocatario(String cadlocatario) {
        this.cadlocatario = cadlocatario;
        return this;
    }

    public void setCadlocatario(String cadlocatario) {
        this.cadlocatario = cadlocatario;
    }

    public String getCadimovel() {
        return cadimovel;
    }

    public Arquivista cadimovel(String cadimovel) {
        this.cadimovel = cadimovel;
        return this;
    }

    public void setCadimovel(String cadimovel) {
        this.cadimovel = cadimovel;
    }

    public Set<Locador> getLocadors() {
        return locadors;
    }

    public Arquivista locadors(Set<Locador> locadors) {
        this.locadors = locadors;
        return this;
    }

    public Arquivista addLocador(Locador locador) {
        this.locadors.add(locador);
        locador.getArquivistas().add(this);
        return this;
    }

    public Arquivista removeLocador(Locador locador) {
        this.locadors.remove(locador);
        locador.getArquivistas().remove(this);
        return this;
    }

    public void setLocadors(Set<Locador> locadors) {
        this.locadors = locadors;
    }

    public Set<Locatario> getLocatarios() {
        return locatarios;
    }

    public Arquivista locatarios(Set<Locatario> locatarios) {
        this.locatarios = locatarios;
        return this;
    }

    public Arquivista addLocatario(Locatario locatario) {
        this.locatarios.add(locatario);
        locatario.getArquivistas().add(this);
        return this;
    }

    public Arquivista removeLocatario(Locatario locatario) {
        this.locatarios.remove(locatario);
        locatario.getArquivistas().remove(this);
        return this;
    }

    public void setLocatarios(Set<Locatario> locatarios) {
        this.locatarios = locatarios;
    }

    public Set<Imovel> getImovels() {
        return imovels;
    }

    public Arquivista imovels(Set<Imovel> imovels) {
        this.imovels = imovels;
        return this;
    }

    public Arquivista addImovel(Imovel imovel) {
        this.imovels.add(imovel);
        imovel.getArquivistas().add(this);
        return this;
    }

    public Arquivista removeImovel(Imovel imovel) {
        this.imovels.remove(imovel);
        imovel.getArquivistas().remove(this);
        return this;
    }

    public void setImovels(Set<Imovel> imovels) {
        this.imovels = imovels;
    }

    public Set<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public Arquivista pagamentos(Set<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
        return this;
    }

    public Arquivista addPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
        pagamento.getArquivistas().add(this);
        return this;
    }

    public Arquivista removePagamento(Pagamento pagamento) {
        this.pagamentos.remove(pagamento);
        pagamento.getArquivistas().remove(this);
        return this;
    }

    public void setPagamentos(Set<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Set<Comprovante> getComprovantes() {
        return comprovantes;
    }

    public Arquivista comprovantes(Set<Comprovante> comprovantes) {
        this.comprovantes = comprovantes;
        return this;
    }

    public Arquivista addComprovante(Comprovante comprovante) {
        this.comprovantes.add(comprovante);
        comprovante.getArquivistas().add(this);
        return this;
    }

    public Arquivista removeComprovante(Comprovante comprovante) {
        this.comprovantes.remove(comprovante);
        comprovante.getArquivistas().remove(this);
        return this;
    }

    public void setComprovantes(Set<Comprovante> comprovantes) {
        this.comprovantes = comprovantes;
    }

    public Set<Vistoria> getVistorias() {
        return vistorias;
    }

    public Arquivista vistorias(Set<Vistoria> vistorias) {
        this.vistorias = vistorias;
        return this;
    }

    public Arquivista addVistoria(Vistoria vistoria) {
        this.vistorias.add(vistoria);
        vistoria.getArquivistas().add(this);
        return this;
    }

    public Arquivista removeVistoria(Vistoria vistoria) {
        this.vistorias.remove(vistoria);
        vistoria.getArquivistas().remove(this);
        return this;
    }

    public void setVistorias(Set<Vistoria> vistorias) {
        this.vistorias = vistorias;
    }

    public Login getLogin() {
        return login;
    }

    public Arquivista login(Login login) {
        this.login = login;
        return this;
    }

    public void setLogin(Login login) {
        this.login = login;
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
        Arquivista arquivista = (Arquivista) o;
        if (arquivista.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), arquivista.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Arquivista{" +
            "id=" + getId() +
            ", cadlocador='" + getCadlocador() + "'" +
            ", cadlocatario='" + getCadlocatario() + "'" +
            ", cadimovel='" + getCadimovel() + "'" +
            "}";
    }
}
