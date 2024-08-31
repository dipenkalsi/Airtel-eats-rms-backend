package com.airteleats.Airtel.eats.controller;

import com.airteleats.Airtel.eats.config.JwtProvider;
import com.airteleats.Airtel.eats.model.Cart;
import com.airteleats.Airtel.eats.model.USER_ROLE;
import com.airteleats.Airtel.eats.model.User;
import com.airteleats.Airtel.eats.repository.CartRepository;
import com.airteleats.Airtel.eats.repository.UserRepository;
import com.airteleats.Airtel.eats.request.LoginRequest;
import com.airteleats.Airtel.eats.response.AuthResponse;
import com.airteleats.Airtel.eats.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth") //endpoints for all mappings inside this controller will start from '/auth'
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //Signup method
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            throw new Exception("An account with this email already exists. Please login to that account.");
        }

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);//encoding the password before storing it into database. Extras security
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    //signin method
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){
        String username = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(username, password);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successful");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    //authentication for siginin
    private Authentication authenticate(String username, String password) {
        //trynna fetch the user from the database with his email
        UserDetails userdetails = customUserDetailsService.loadUserByUsername(username);

        //if user does not exist
        if(userdetails==null){
            throw new BadCredentialsException("A user does not exist with this username.");
        }

        //if the password is incorrect
        if(!passwordEncoder.matches(password, userdetails.getPassword())){
            throw new BadCredentialsException("Incorrect password.");
        }
        return new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
    }

}
