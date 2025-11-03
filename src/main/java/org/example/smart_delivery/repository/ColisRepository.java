package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Colis;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis, String> {
    List<Colis> findColisByStatut(ColisStatus statut);
}
