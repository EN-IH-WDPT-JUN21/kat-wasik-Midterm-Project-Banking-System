package com.example.BankingSystem.repository;

import com.example.BankingSystem.dao.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Integer> {
}
