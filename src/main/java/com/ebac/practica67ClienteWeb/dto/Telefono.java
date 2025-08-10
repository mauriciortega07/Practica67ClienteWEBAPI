package com.ebac.practica67ClienteWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Telefono {
    private int idTelefono;
    private String tipo;
    private int lada;
    private String numero;
}
