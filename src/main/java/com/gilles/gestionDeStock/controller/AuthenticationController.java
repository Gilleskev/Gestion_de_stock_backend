package com.gilles.gestionDeStock.controller;


import com.gilles.gestionDeStock.dto.auth.AuthenticationRequest;
import com.gilles.gestionDeStock.dto.auth.AuthenticationResponse;
import com.gilles.gestionDeStock.model.auth.ExtendedUser;
import com.gilles.gestionDeStock.services.auth.ApplicationUserDetailsService;
import com.gilles.gestionDeStock.utils.Constants;
import com.gilles.gestionDeStock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.AUTHENTICATION_ENDPOINT)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>  authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());

        final  String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);

        return  ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());

    }
}
