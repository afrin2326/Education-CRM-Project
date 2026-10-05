package com.iostream.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.iostream.main.entities.Course;
import com.iostream.main.entities.User;
import com.iostream.main.repositories.UserRepository;
import com.iostream.main.services.CourseService;
import com.iostream.main.services.UserService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("sessionUser")
public class UserController 
{
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping({"/","/index"})
    public String openHomePage(Model model)
    {
    	List<Course> courseList=courseService.getAllCourseDetails();
    	model.addAttribute("courseList", courseList);
        return "index";
    }
    
    @GetMapping("/login")
    public String openLoginPage(Model model)
    {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/loginForm")
    public String handleLoginForm(@ModelAttribute("user") User user, Model model)
    {
        boolean isAuthenticated = userService.loginUserService(user.getEmail(), user.getPassword());
        
        if(isAuthenticated)
        {
        	User authenticatedUser=userRepository.findByEmail(user.getEmail());
        	model.addAttribute("sessionUser", authenticatedUser);
        	
            return "user-profile";
        }
        else
        {
            model.addAttribute("errorMsg", "Incorrect Email Id or Password");
            return "login";
        }
    }
    
    @GetMapping("/register")
    public String openRegisterPage(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/regForm")
    public String handleRegForm(@Valid @ModelAttribute("user") User user, 
                               BindingResult result, 
                               Model model)
    {
        if(result.hasErrors())
        {
            // Return to register page with validation errors
            return "register";
        }
        
        try
        {
            // Check if email already exists
            if(userService.emailExists(user.getEmail())) {
                model.addAttribute("emailError", "Email already registered");
                return "register";
            }
            
            userService.registerUserService(user);
            model.addAttribute("successMsg", "Registration successful! Please login.");
            model.addAttribute("user", new User());
            return "register";
        }
        catch(Exception e)
        {
            model.addAttribute("errorMsg", "Registration failed. Please try again.");
            return "register";
        }
    }
    
    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus)
    {
        sessionStatus.setComplete();
        return "login";
    }
    
    @GetMapping("/my-enrollments")
    public String openMyCoursesPage(Model model)
    {
        model.addAttribute("user", new User());
        return "my-enrollments";
    }
}