package com.example.BankingSystem.repository;

import com.example.BankingSystem.dao.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Integer> {
}
