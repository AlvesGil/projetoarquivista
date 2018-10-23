package br.ufpa.arquivista.repository;

import br.ufpa.arquivista.domain.Locatario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Locatario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocatarioRepository extends JpaRepository<Locatario, Long> {

}
