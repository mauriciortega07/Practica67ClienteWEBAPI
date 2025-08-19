package com.ebac.practica67ClienteWeb.restTemplate;

import com.ebac.practica67ClienteWeb.dto.Usuario;
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
public class usuarioCliente {
    private RestTemplate restTemplate;
    private String urlApi = "http://localhost:8080/usuarios";

    @Autowired
    public usuarioCliente(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String guardarUsuario(Usuario usuario){
        HttpEntity<Usuario> usuarioHttpEntity = new HttpEntity<>(usuario);

        try {
            ResponseEntity<String> guardarUsuario = restTemplate.exchange(urlApi, HttpMethod.POST, usuarioHttpEntity, String.class);
            log.info("Registro Realizado");
            return guardarUsuario.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.error(e.getMessage());
            String error = e.getResponseBodyAsString();
            return error;
        }
    }

    public String obtenerUsuarios(){
        try {
            ResponseEntity<String> obtenerUsuarios = restTemplate.exchange(urlApi, HttpMethod.GET, null, String.class);
            log.info("Registros Obtenidos");
            return obtenerUsuarios.getBody();
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            log.error(e.getMessage());
            String error = e.getResponseBodyAsString();
            return error;
        }
    }

    public String obtenerUsuarioPorID(int idUsuario) {
        String urlApiID = urlApi + "/" + idUsuario;
        try {
            ResponseEntity<String> obtenerUsuarioEntity = restTemplate.exchange(urlApiID, HttpMethod.GET, null, String.class);
            log.info("Registro Obtenido");
            return obtenerUsuarioEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.error(e.getMessage());
            String error = e.getResponseBodyAsString();
            return error;
        }
    }

    public String actualizarUsuarioPorID(int idUsuario, Usuario usuarioActualizado){
        String urlApiID = urlApi + "/" + idUsuario;
        HttpEntity<Usuario> usuarioHttpEntity = new HttpEntity<>(usuarioActualizado);
        try {
            ResponseEntity<String> usuariosResponse = restTemplate.exchange(urlApiID, HttpMethod.PUT, usuarioHttpEntity, String.class);
            log.info("Registro Actualizado");
            return usuariosResponse.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.error(e.getMessage());
            String error = e.getResponseBodyAsString();
            return error;
        }
    }

    public String eliminarUsuarioPorID(int idUsuario){
        String urlApiID = urlApi + "/" + idUsuario;
        try {
            ResponseEntity<String> usuariosResponse= restTemplate.exchange(urlApiID, HttpMethod.DELETE, null, String.class);
            log.info("Registro eliminado");
            return usuariosResponse.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            log.error(e.getMessage());
            String error = e.getResponseBodyAsString();
            return error;
        }
    }
}
