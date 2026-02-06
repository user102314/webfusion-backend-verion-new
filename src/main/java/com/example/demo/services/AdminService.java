package com.example.demo.services;

import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;

import java.util.List;

public interface AdminService {
    LoginResponse login(LoginRequest loginRequest);

}