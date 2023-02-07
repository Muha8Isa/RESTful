package se.lexicon.restful.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.restful.model.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String name);

    // select * from roles order by id desc;
    List<Role> findAllByOrderByIdDesc();
}
