package com.itrex.java.lab.service.serviceimpl;

import org.modelmapper.ModelMapper;
import com.itrex.java.lab.dto.RoleDTO;
import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.service.RoleService;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(@Qualifier("hibernateRoleRepositoryImpl") RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleDTO> getAllRoles() throws ServiceException {
        List<RoleDTO> allRoleDTOList = new ArrayList<>();
        try {
            List<Role> roles = roleRepository.getAllRoles();

            if (roles.size() != 0) {
                allRoleDTOList = roles.stream()
                        .map(this::convertRoleIntoRoleDTO)
                        .collect(Collectors.toList());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return allRoleDTOList;
    }

    @Override
    public Optional<RoleDTO> getRoleByName(String name) throws ServiceException {
        RoleDTO roleDTO = null;
        try {
            Optional<Role> role = roleRepository.getRoleByName(name);

            if (role.isPresent()) {
                roleDTO = convertRoleIntoRoleDTO(role.get());
            }
        } catch (RepositoryException ex) {
            throw  new ServiceException(ex.getMessage());
        }

        return Optional.ofNullable(roleDTO);
    }

    @Override
    public RoleDTO add(Role role) throws ServiceException {
        RoleDTO newRoleDTO = null;
        try {
            Role newRole = roleRepository.add(role);

            if (newRole != null) {
                newRoleDTO = convertRoleIntoRoleDTO(newRole);
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return newRoleDTO;
    }

    private RoleDTO convertRoleIntoRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

}
