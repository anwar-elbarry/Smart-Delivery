package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, String> {
}
