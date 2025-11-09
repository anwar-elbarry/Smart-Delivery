package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Colis;
import org.example.smart_delivery.entity.Zone;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis, String> {
    Page<Colis> findColisByStatut(ColisStatus statut , Pageable pageable);

    Page<Colis> findColisByPriorite(Priority priorite,Pageable pageable);

    Page<Colis> findColisByZone(Zone zone, Pageable pageable);
}
