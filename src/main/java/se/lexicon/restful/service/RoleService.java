package se.lexicon.restful.service;

import se.lexicon.restful.model.entity.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();
    RoleDto findById(Integer roleId);
    RoleDto create(RoleDto roleDto);
    void update(RoleDto roleDto);
    void delete(Integer roleId);
}
