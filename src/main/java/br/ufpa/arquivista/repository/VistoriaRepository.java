package br.ufpa.arquivista.repository;

import br.ufpa.arquivista.domain.Vistoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vistoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VistoriaRepository extends JpaRepository<Vistoria, Long> {

}
