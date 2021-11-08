package com.itrex.java.lab.service.serviceimpl;

import org.modelmapper.ModelMapper;
import com.itrex.java.lab.dto.UserDTO;
import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.service.UserService;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;        //????????
    private final ModelMapper modelMapper;

    public UserServiceImpl(@Qualifier("hibernateUserRepositoryImpl")UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;       //??????????
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() throws ServiceException {
        List<UserDTO> allUserDTOList = new ArrayList<>();
        try {
            List<User> users = userRepository.getAllUsers();

            if (users.size() != 0) {
                allUserDTOList = users.stream()
                        .map(this::convertUserIntoUserDTO)
                        .collect(Collectors.toList());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return allUserDTOList;
    }

    @Override
    public List<UserDTO> getAllUsersByRole(String role) throws ServiceException {
        List<UserDTO> userByRoleDTOList = new ArrayList<>();
        try {
            List<User> users = userRepository.getAllUsersByRole(role);

            if(users.size() != 0) {
                userByRoleDTOList = users.stream()
                        .map(this::convertUserIntoUserDTO)
                        .collect(Collectors.toList());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return userByRoleDTOList;
    }

    @Override
    public Optional<UserDTO> getUserById(int userId) throws ServiceException {
        UserDTO userDTO = null;
        try {
            Optional<User> user = userRepository.getUserById(userId);

            if (user.isPresent()) {
                userDTO = convertUserIntoUserDTO(user.get());
            }
        } catch (RepositoryException ex) {
            throw  new ServiceException(ex.getMessage());
        }

        return Optional.ofNullable(userDTO);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) throws ServiceException {
        UserDTO userDTO = null;
        try {
            Optional<User> user = userRepository.getUserByEmail(email);

            if (user.isPresent()) {
                userDTO = convertUserIntoUserDTO(user.get());
            }
        } catch (RepositoryException ex) {
            throw  new ServiceException(ex.getMessage());

        }

        return Optional.ofNullable(userDTO);
    }

    @Override
    public boolean deleteUserById(int userId) throws ServiceException {
        boolean isDelete;
        try {
            isDelete = userRepository.deleteUserById(userId);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return isDelete;
    }

    @Override
    public UserDTO add(User user) throws ServiceException {
        UserDTO newUserDTO = null;
        try {
            User newUser = userRepository.add(user);

            if (newUser != null) {
                newUserDTO = convertUserIntoUserDTO(newUser);
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return newUserDTO;
    }

//    @Override
//    public void addAll(List<User> users) throws ServiceException {
//        List<UserDTO> addAllUserDTOList = new ArrayList<>();
//        try {
//
//        }
//    }

    @Override
    public boolean assignRole(User user, Role role) throws ServiceException {
        boolean isAssignRole;
        try {
            isAssignRole = userRepository.assignRole(user, role);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return isAssignRole;
    }

    private UserDTO convertUserIntoUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
