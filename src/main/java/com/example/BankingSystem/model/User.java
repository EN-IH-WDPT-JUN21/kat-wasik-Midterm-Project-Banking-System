package com.example.BankingSystem.model;

import com.example.BankingSystem.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String username;
    private String password;

    @ManyToOne
    private Role role;

    public User(String name, String username, String password, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public boolean isAdmin() {
        return getRole().getName().equals(RoleName.ADMIN);
    }
    
    public boolean isOwner(Account account) {
        AccountHolder primaryOwner = account.getPrimaryOwner();
        Optional<AccountHolder> secondaryOwnerOptional = Optional.ofNullable(account.getSecondaryOwner());
        Integer userId = getId();

        if (userId.equals(primaryOwner.getId())) {
            return true;
        } else if (secondaryOwnerOptional.isPresent() && userId.equals(secondaryOwnerOptional.get().getId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
