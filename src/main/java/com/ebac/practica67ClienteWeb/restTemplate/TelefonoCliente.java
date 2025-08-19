package com.ebac.practica67ClienteWeb.restTemplate;

import com.ebac.practica67ClienteWeb.dto.Telefono;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class TelefonoCliente {
    private final RestTemplate restTemplate;
    private String urlApi = "http://localhost:8080/telefonos";

    @Autowired
    public TelefonoCliente(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String guardarTelefono(Telefono telefono){
        HttpEntity<Telefono> telefonoHttpEntity = new HttpEntity<>(telefono);
        try {
            ResponseEntity<String> telefonoResponseEntity = restTemplate.exchange(urlApi, HttpMethod.POST, telefonoHttpEntity, String.class);
            log.info("Registro de Telefono Realizado");
            return telefonoResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException error){
            log.warn(error.getMessage());
            return error.getResponseBodyAsString();
        }

    }

    public String obtenerTelefonos(){
        try {
            ResponseEntity<String> telefonoResponse = restTemplate.exchange(urlApi, HttpMethod.GET, null, String.class);
            log.info("Telefonos Obtenidos");
            return telefonoResponse.getBody();
        } catch (HttpServerErrorException | HttpClientErrorException error){
            log.warn(error.getMessage());
            return error.getResponseBodyAsString();
        }
    }

    public String obtenerTelefonoPorID(int idTelefono){
        String urlIdTelefono = urlApi + "/" + idTelefono;

        try {
             ResponseEntity<String> telefonoResponse = restTemplate.exchange(urlIdTelefono, HttpMethod.GET, null, String.class);
             log.info("Telefono obtenido");
             return telefonoResponse.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException error){
            log.warn(error.getMessage());
            return error.getResponseBodyAsString();
        }
    }

    public String actualizarTelefonoPorID(int idTelefono, Telefono telefonoActualizado){
        String urlActualizarUsuario = urlApi + "/" + idTelefono;
        HttpEntity<Telefono> telefonoHttpEntity = new HttpEntity<>(telefonoActualizado);

        try {
            ResponseEntity<String> telefonoResponse = restTemplate.exchange(urlActualizarUsuario, HttpMethod.PUT, telefonoHttpEntity, String.class);
            log.info("Telefono Obtenido");
            return telefonoResponse.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.warn(e.getMessage());
            return e.getResponseBodyAsString();
        }
    }

    public String eliminarTelefonoPorId(int idTelefono){
        String urlEliminarUsuario = urlApi + "/" + idTelefono;

        try {
            ResponseEntity<String> telefonoResponse = restTemplate.exchange(urlEliminarUsuario, HttpMethod.DELETE, null, String.class);
            log.info("Telefono Eliminado");
            return telefonoResponse.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.warn(e.getMessage());
            return e.getResponseBodyAsString();
        }
    }
}
