package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Colis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColisRepository extends JpaRepository<Colis, String> {
}
