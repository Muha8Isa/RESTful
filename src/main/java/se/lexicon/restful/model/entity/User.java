package se.lexicon.restful.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.lexicon.restful.exception.DataDuplicateException;
import se.lexicon.restful.exception.DataNotFoundException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class User {
    @Id
    @Column(updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private boolean expired;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "USERNAME")},
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addRole(Role role){
        if(roles.contains(role)){
            throw new DataDuplicateException("Duplicated data!");
        }
        roles.add(role);
    }

    public void removeRole(Role role){
        if(!roles.contains(role)){
            throw new DataNotFoundException("Data not found!");
        }
        roles.remove(role);
    }
}
