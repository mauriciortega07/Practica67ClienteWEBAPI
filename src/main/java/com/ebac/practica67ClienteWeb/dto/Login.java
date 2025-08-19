package com.ebac.practica67ClienteWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private int idLogin;
    private String nombre;
    private String email;
    private String password;
    private int edad;
}
