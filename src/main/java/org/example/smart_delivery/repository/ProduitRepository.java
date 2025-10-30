package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, String> {
}
