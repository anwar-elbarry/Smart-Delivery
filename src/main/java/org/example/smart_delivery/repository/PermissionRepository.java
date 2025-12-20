package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,String> {
}
