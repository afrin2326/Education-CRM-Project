package com.iostream.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iostream.main.entities.User;
import com.iostream.main.repositories.UserRepository;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;
    
    public void registerUserService(User user)
    {
        userRepository.save(user);    
    }
    
    public boolean loginUserService(String email,String password)
    {
        User user=userRepository.findByEmail(email);
        if(user!=null)
        {
            return password.equals(user.getPassword());    
        }
        else
        {
            return false;
        }
    }
    
    // Add this method to check if email already exists
    public boolean emailExists(String email)
    {
        User user = userRepository.findByEmail(email);
        return user != null;
    }
}