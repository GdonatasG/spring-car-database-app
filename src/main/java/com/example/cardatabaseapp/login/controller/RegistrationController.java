package com.example.cardatabaseapp.login.controller;

import com.example.cardatabaseapp.login.model.ERole;
import com.example.cardatabaseapp.login.model.Role;
import com.example.cardatabaseapp.login.model.User;
import com.example.cardatabaseapp.login.payload.request.SignupRequest;
import com.example.cardatabaseapp.login.repository.RoleRepository;
import com.example.cardatabaseapp.login.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    final
    PasswordEncoder encoder;

    public RegistrationController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        super();
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @ModelAttribute("user")
    public SignupRequest request() {
        return new SignupRequest();
    }

    @GetMapping
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return "redirect:/registration?userExists";
        }
        try {
            final User user = new User(request.getUsername(), encoder.encode(request.getPassword()));
            Set<Role> roles = new HashSet<>();

            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);

            user.setRoles(roles);
            userRepository.save(user);
            return "redirect:/login?success";
        } catch (Exception e) {
            return "redirect:/registration?error";
        }
    }
}
