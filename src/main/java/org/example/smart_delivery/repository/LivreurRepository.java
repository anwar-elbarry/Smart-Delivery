package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Livreur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LivreurRepository extends JpaRepository<Livreur, String> {
    @Query("""
  select l from Livreur l
  left join l.user u
  left join l.zoneAssigne z
  where
    lower(u.nom) like lower(concat('%', :q, '%'))
    or lower(u.prenom) like lower(concat('%', :q, '%'))
    or u.telephone like concat('%', :q, '%')
    or lower(l.vehicule) like lower(concat('%', :q, '%'))
    or lower(z.nome) like lower(concat('%', :q, '%'))
""")
    Page<Livreur> search(@Param("q") String q, Pageable pageable);
}
