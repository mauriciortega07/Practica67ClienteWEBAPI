package com.ebac.practica67ClienteWeb.restTemplate;

import com.ebac.practica67ClienteWeb.dto.Login;
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
public class LoginCliente {
    private final RestTemplate restTemplate;
    private String apiUrl = "http://localhost:8080/logins";

    @Autowired
    public LoginCliente (RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String guardarLogin(Login login) {
        HttpEntity<Login> loginHttpEntity = new HttpEntity<>(login);
        try {
            ResponseEntity<String> loginCreado = restTemplate.exchange(apiUrl, HttpMethod.POST, loginHttpEntity, String.class);
            log.info("Login Registrado");
            return loginCreado.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.error("Login Cliente - guardarLogin() {}", e.getMessage());
            return e.getResponseBodyAsString();
        }
    }

    public String obtenerLogins(){
        try {
            ResponseEntity<String> logins = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
            log.info("Logins Obtenidos");
            return logins.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.error("Login Cliente - obtenerLogins() {}", e.getMessage());
            return e.getResponseBodyAsString();
        }
    }

    public String obtenerLoginPorID(int idLogin){
        String urlPorID = apiUrl + "/" + idLogin;

        try {
            ResponseEntity<String> login = restTemplate.exchange(urlPorID, HttpMethod.GET, null, String.class);
            log.info("Login Obtenido");
            return login.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.error("Login Cliente - obtenerLoginPorID() {}", e.getMessage());
            return e.getResponseBodyAsString();
        }
    }

    public String actualizarPorID(int idLogin, Login loginActualizado){
        String urlActualizarPorID = apiUrl + "/" + idLogin;
        HttpEntity<Login> loginHttpEntity = new HttpEntity<>(loginActualizado);
        try {
            ResponseEntity<String> loginAct = restTemplate.exchange(urlActualizarPorID, HttpMethod.PUT, loginHttpEntity, String.class);
            log.info("Login Actualizado");
            return loginAct.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.info("Login Cliente - actualizarPorID() {}", e.getMessage());
            return e.getResponseBodyAsString();
        }
    }

    public String eliminarPorID(int idLogin){
        String urlEliminarPorID = apiUrl + "/" + idLogin;

        try {
            ResponseEntity<String> loginEliminado = restTemplate.exchange(urlEliminarPorID, HttpMethod.DELETE, null, String.class);
            log.info("Login Eliminado");
            return loginEliminado.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.warn("Login Cliente - eliminarPorID() {}", e.getMessage());
            return e.getResponseBodyAsString();
        }
    }
}
