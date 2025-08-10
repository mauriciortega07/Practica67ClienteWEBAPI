package com.ebac.practica67ClienteWeb.dto;

import lombok.Data;

@Data
public class ResponseWrapper<T> {
    private String message;
    private ResponseEntity<T> responseEntity;
    private boolean succes;
}
