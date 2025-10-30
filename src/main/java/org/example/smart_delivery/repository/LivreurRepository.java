package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreurRepository extends JpaRepository<Livreur, String> {
}
