package com.example.BankingSystem;

import com.example.BankingSystem.enums.RoleName;
import com.example.BankingSystem.model.Role;
import com.example.BankingSystem.repository.RoleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankingSystemApplication {
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
	}

	// Roles are created automatically on application startup
	@Bean
	InitializingBean createRoles() {
		return () -> {
			for (RoleName roleName : RoleName.values()) {
				roleRepository.save(new Role(roleName));
			}
		};
	}
}


