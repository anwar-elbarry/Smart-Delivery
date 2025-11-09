package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Colis;
import org.example.smart_delivery.entity.Zone;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis, String> {
    Page<Colis> findColisByStatut(ColisStatus statut , Pageable pageable);

    Page<Colis> findColisByPriorite(Priority priorite,Pageable pageable);

    Page<Colis> findColisByZone(Zone zone, Pageable pageable);

    @Query("""
  select c from Colis c
  left join c.clientExpediteur ce
  left join c.destinataire de
  left join c.livreur l
  left join l.user lu
  where
    lower(c.description) like lower(concat('%', :q, '%'))
    or lower(c.villeDestination) like lower(concat('%', :q, '%'))
    or lower(ce.nom) like lower(concat('%', :q, '%'))
    or lower(ce.prenom) like lower(concat('%', :q, '%'))
    or lower(de.nom) like lower(concat('%', :q, '%'))
    or lower(de.prenom) like lower(concat('%', :q, '%'))
    or lower(lu.nom) like lower(concat('%', :q, '%'))
    or lower(lu.prenom) like lower(concat('%', :q, '%'))
    or c.id like concat('%', :q, '%')
""")
    Page<Colis> search(@Param("q") String q, Pageable pageable);
}
