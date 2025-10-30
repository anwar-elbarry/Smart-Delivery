package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.HistoriqueLivraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueLivraisonRepository extends JpaRepository<HistoriqueLivraison, String> {
}
