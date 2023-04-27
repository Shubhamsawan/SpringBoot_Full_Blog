package com.sprinboot.blog.service;

import com.sprinboot.blog.payload.LoginDto;
import com.sprinboot.blog.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
