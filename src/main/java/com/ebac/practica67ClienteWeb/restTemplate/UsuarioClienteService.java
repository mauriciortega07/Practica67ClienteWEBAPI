package com.ebac.practica67ClienteWeb.restTemplate;

import com.ebac.practica67ClienteWeb.dto.ConvertidorJsonAResponseWrapper;
import com.ebac.practica67ClienteWeb.dto.ResponseWrapper;
import com.ebac.practica67ClienteWeb.dto.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class usuarioClienteService {
    private final usuarioCliente usuarioCliente;
    private final ObjectMapper objectMapper;
    private final ConvertidorJsonAResponseWrapper<Usuario> convertidorJsonAResponseWrapper;
    private final ConvertidorJsonAResponseWrapper<List<Usuario>> generadorRespuestaListasServidor;
    private final ConvertidorJsonAResponseWrapper<Void> generadorRespuestaVaciaServidor;

    @Autowired
    public usuarioClienteService(usuarioCliente usuarioCliente,
                                 ObjectMapper objectMapper,
                                 ConvertidorJsonAResponseWrapper<Usuario> convertidorJsonAResponseWrapper,
                                 ConvertidorJsonAResponseWrapper<List<Usuario>> generadorRespuestaListasServidor,
                                 ConvertidorJsonAResponseWrapper<Void> generadorRespuestaVaciaServidor) {
        this.usuarioCliente = usuarioCliente;
        this.objectMapper = objectMapper;
        this.convertidorJsonAResponseWrapper = convertidorJsonAResponseWrapper;
        this.generadorRespuestaListasServidor = generadorRespuestaListasServidor;
        this.generadorRespuestaVaciaServidor = generadorRespuestaVaciaServidor;
    }




    public Optional<Usuario> guardarUsuario(Usuario usuario) {
        String usuarioNuevo = usuarioCliente.guardarUsuario(usuario);

        try {
            ResponseWrapper<Usuario> usuarioResponseWrapper = objectMapper.readValue(usuarioNuevo, new TypeReference<>(){});
            if (usuarioResponseWrapper.isSucces()) {
                log.info("Service Succes: {}", usuarioResponseWrapper.getMessage());
            } else {
                log.warn("Service Failed: {}", usuarioResponseWrapper.getMessage());
            }

            return Optional.ofNullable(usuarioResponseWrapper.getResponseEntity().getBody());
        } catch (JsonProcessingException e){
            log.warn("Error al parsear la respuesta del servidor {}", e.getMessage());
            return Optional.empty();
        }

    }

    public Optional<List<Usuario>> obtenerUsuarios() {

        String usuarios = usuarioCliente.obtenerUsuarios();
        TypeReference<ResponseWrapper<List<Usuario>>> typeReference2 = new TypeReference<>(){};
        /*try {
            ResponseWrapper<List<Usuario>> usuariosWrapper = objectMapper.readValue(usuarios, new TypeReference<>(){});
            log.info(usuariosWrapper.getMessage());
            List<Usuario> usuarioList = usuariosWrapper.getResponseEntity().getBody();
            return Optional.ofNullable(usuarioList);
        } catch (JsonProcessingException e){
            log.warn("Error al parsear la respuesta del servidor {}",e.getMessage());
            return Optional.empty();
        }*/

        return generadorRespuestaListasServidor.respuestaAParsear(usuarios, typeReference2);
    }

    public Optional<Usuario> obtenerUsuarioPorID(int idUsuario){
        String usuario = usuarioCliente.obtenerUsuarioPorID(idUsuario);

        TypeReference<ResponseWrapper<Usuario>> typeReference = new TypeReference<>(){};
        /*try {
            ResponseWrapper<Usuario> usuarioWrapper = objectMapper.readValue(usuario, new TypeReference<>(){});
            log.info(usuarioWrapper.getMessage());
            Usuario usuarioObtenido = usuarioWrapper.getResponseEntity().getBody();
            return Optional.ofNullable(usuarioObtenido);
        } catch (JsonProcessingException e){
            log.warn("Error al parsear la respuesta del servidor: {}", e.getMessage());
            return Optional.empty();
        }*/

        return convertidorJsonAResponseWrapper.respuestaAParsear(usuario, typeReference);
    }

    public Optional<Usuario> actualizarPorID(int idUsuario, Usuario usuarioActualizado){
        String usuario = usuarioCliente.actualizarUsuarioPorID(idUsuario, usuarioActualizado);
        TypeReference<ResponseWrapper<Usuario>> typeReference = new TypeReference<>() {};

        return convertidorJsonAResponseWrapper.respuestaAParsear(usuario, typeReference);
    }

    public Optional<Void> eliminarUsuarioPorID(int idUsuario){
        String usuario = usuarioCliente.eliminarUsuarioPorID(idUsuario);
        TypeReference<ResponseWrapper<Void>> typeReference = new TypeReference<>() {};

        return generadorRespuestaVaciaServidor.respuestaAParsear(usuario, typeReference);
    }




}
