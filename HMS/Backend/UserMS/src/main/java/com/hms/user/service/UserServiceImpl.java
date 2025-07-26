package com.hms.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.user.dto.UserDTO;
import com.hms.user.entity.User;
import com.hms.user.exception.HmsException;
import com.hms.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void resisterUser(UserDTO userDTO) throws HmsException {
        Optional<User> opt = userRepository.findByEmail(userDTO.getEmail());
        if (opt.isPresent()) {
            throw new HmsException("User already exists with email");
        }
        ;
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userDTO.toEntity());
    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) throws HmsException {
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new HmsException("User not found"));
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new HmsException("Invalid password");

        }
        user.setPassword(null); // Clear password before returning
        return user.toDTO(); 
    }

    @Override
    public UserDTO getUserById(Long id) throws HmsException {
        return userRepository.findById(id).orElseThrow(() -> new HmsException("User not found")).toDTO();
    }

    @Override
    public void updateUser(UserDTO userDTO)  {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }
    

    @Override
    public UserDTO getUser(String email) throws HmsException {
        return userRepository.findByEmail(email).orElseThrow(() -> new HmsException("User not found with email: " + email)).toDTO();
    }

    @Override
    public UserDetails loadUserByUsername(Object email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
    


}
