package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.ColisProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColisProduitRepository extends JpaRepository<ColisProduit, String> {
}
