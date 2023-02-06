package se.lexicon.restful.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class User {
    @Id
    private String username;
    @Column(nullable = false)
    private String password;
    private boolean expired;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Role> roles = new HashSet<>();
}
