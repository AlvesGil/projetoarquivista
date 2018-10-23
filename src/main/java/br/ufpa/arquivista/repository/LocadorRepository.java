package br.ufpa.arquivista.repository;

import br.ufpa.arquivista.domain.Locador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Locador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocadorRepository extends JpaRepository<Locador, Long> {

}
