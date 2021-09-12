package com.example.BankingSystem.repository;

import com.example.BankingSystem.dao.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepositoty extends JpaRepository<ThirdParty, Integer> {
}
