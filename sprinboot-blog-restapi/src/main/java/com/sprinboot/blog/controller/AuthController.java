package com.sprinboot.blog.controller;

import com.sprinboot.blog.payload.JwtAuthResponse;
import com.sprinboot.blog.payload.LoginDto;
import com.sprinboot.blog.payload.RegisterDto;
import com.sprinboot.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// http://localhost:8080/swagger-ui/index.html
// http://localhost:8080/v3/api-docs
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //Build login api
//    url : http://localhost:8080/api/auth/signin or login !! Authentication bearer token
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    //Build register rest api
    // url http://localhost:8080/api/auth/signup or register
    @PostMapping(value = {"/register", "/signup"} )
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

// Before posting data we need to do login using follwind step
//1. access url http://localhost:8080/api/auth/login
//2. {
//        "usernameOrEmail":"admin@gmail.com",
//        "password":"admin"
//        }
//3. then copy : accesstoken
//4. Then go to api page -> Authorization -> Type: Bearer Token -> copy the token -> then data will send

//login id
//        {
//                "usernameOrEmail":"shubham@gmail.com",
//                "password":"shubham"
//                }
//                     or
//{
//    "usernameOrEmail":"admin",
//        "password":"admin"
//}

//Register

//{
//        "name":"tony",
//        "username":"tony",
//        "email":"tony@gmail.com",
//        "password":"tony"
//        }