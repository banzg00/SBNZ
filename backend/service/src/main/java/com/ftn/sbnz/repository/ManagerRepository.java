package com.ftn.sbnz.repository;

import com.ftn.sbnz.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByUsernameAndPassword(String username, String password);
}
