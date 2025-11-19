package org.example.smart_delivery.repository;

import org.example.smart_delivery.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrTelephoneContainingOrEmailContainingIgnoreCaseOrAdressContainingIgnoreCase(
            String nom, String prenom, String telephone, String email, String adress, Pageable pageable);
    
    @Query("""
      select u from User u
      where
        lower(u.nom) like lower(concat('%', :q, '%'))
        or lower(u.prenom) like lower(concat('%', :q, '%'))
        or u.telephone like concat('%', :q, '%')
        or lower(u.email) like lower(concat('%', :q, '%'))
        or lower(u.adress) like lower(concat('%', :q, '%'))
    """)
    Page<User> search(@Param("q") String q, Pageable pageable);
    
    Optional<User> findUserByEmail(String email);
    
    Optional<User> findUserByVerificationCode(String verificationCode);
}
