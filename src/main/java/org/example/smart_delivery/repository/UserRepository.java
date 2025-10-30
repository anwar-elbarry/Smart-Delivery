package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
