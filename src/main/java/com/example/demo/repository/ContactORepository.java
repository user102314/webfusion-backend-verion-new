package com.example.demo.repository;

import com.example.demo.model.ContactO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactORepository extends JpaRepository<ContactO, Long> {
    List<ContactO> findByEmail(String email);
}