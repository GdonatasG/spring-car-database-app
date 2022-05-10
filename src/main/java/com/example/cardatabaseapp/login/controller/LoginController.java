package com.example.cardatabaseapp.login.controller;


import com.example.cardatabaseapp.login.payload.request.LoginRequest;
import com.example.cardatabaseapp.login.repository.UserRepository;
import com.example.cardatabaseapp.login.security.jwt.JwtUtils;
import com.example.cardatabaseapp.login.security.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final
    UserRepository userRepository;
    private final
    AuthenticationManager authenticationManager;
    private final
    JwtUtils jwtUtils;

    public LoginController(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @ModelAttribute("user")
    public LoginRequest request() {
        return new LoginRequest();
    }

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    /*@PostMapping
    public String authenticateUser(@ModelAttribute("user") LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            //jwtUtils.generateJwtCookie(userDetails);

            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }*/
}
