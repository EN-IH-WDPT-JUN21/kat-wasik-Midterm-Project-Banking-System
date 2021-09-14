package com.example.BankingSystem.repository;

import com.example.BankingSystem.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
    Optional<AccountHolder> findByName(String name);
}
