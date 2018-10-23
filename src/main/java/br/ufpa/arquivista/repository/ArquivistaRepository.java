package br.ufpa.arquivista.repository;

import br.ufpa.arquivista.domain.Arquivista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Arquivista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArquivistaRepository extends JpaRepository<Arquivista, Long> {

    @Query(value = "select distinct arquivista from Arquivista arquivista left join fetch arquivista.locadors left join fetch arquivista.locatarios left join fetch arquivista.imovels left join fetch arquivista.pagamentos left join fetch arquivista.comprovantes left join fetch arquivista.vistorias",
        countQuery = "select count(distinct arquivista) from Arquivista arquivista")
    Page<Arquivista> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct arquivista from Arquivista arquivista left join fetch arquivista.locadors left join fetch arquivista.locatarios left join fetch arquivista.imovels left join fetch arquivista.pagamentos left join fetch arquivista.comprovantes left join fetch arquivista.vistorias")
    List<Arquivista> findAllWithEagerRelationships();

    @Query("select arquivista from Arquivista arquivista left join fetch arquivista.locadors left join fetch arquivista.locatarios left join fetch arquivista.imovels left join fetch arquivista.pagamentos left join fetch arquivista.comprovantes left join fetch arquivista.vistorias where arquivista.id =:id")
    Optional<Arquivista> findOneWithEagerRelationships(@Param("id") Long id);

}
