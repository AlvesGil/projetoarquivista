package br.ufpa.arquivista.repository;

import br.ufpa.arquivista.domain.Imovel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Imovel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

}
