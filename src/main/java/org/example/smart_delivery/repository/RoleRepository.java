package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {

Optional<Role> findRoleByName(String name);
}
