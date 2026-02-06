package com.example.demo.repository;

import com.example.demo.model.AvisO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvisORepository extends JpaRepository<AvisO, Long> {
    List<AvisO> findByEtat(String etat);
}