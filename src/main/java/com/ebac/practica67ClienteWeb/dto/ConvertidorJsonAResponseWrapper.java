package com.ebac.practica67ClienteWeb.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ConvertidorJsonAResponseWrapper<T> {

    @Autowired
    ObjectMapper objectMapper;

    public Optional<T> respuestaAParsear(String respuesta, TypeReference<ResponseWrapper<T>> typeReference){
        try {
            ResponseWrapper<T> usuarioWrapper = objectMapper.readValue(respuesta, typeReference);
            log.info(usuarioWrapper.getMessage());
            T usuarioContent = usuarioWrapper.getResponseEntity().getBody();
            return Optional.ofNullable(usuarioContent);
        } catch (JsonProcessingException e){
            log.warn("Error al parsear la respuesta del servidor {}",e.getMessage());
            return Optional.empty();
        }
    }
}
