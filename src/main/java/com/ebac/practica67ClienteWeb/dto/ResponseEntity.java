package com.ebac.practica67ClienteWeb.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseEntity<T> {
    Map<String, Object> headers;
    T body;
    int statusCodeValue;
    String statusCode;
}
