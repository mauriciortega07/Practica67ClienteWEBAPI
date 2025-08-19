package com.ebac.practica67ClienteWeb.restTemplate;

import com.ebac.practica67ClienteWeb.dto.ConvertidorJsonAResponseWrapper;
import com.ebac.practica67ClienteWeb.dto.ResponseWrapper;
import com.ebac.practica67ClienteWeb.dto.Telefono;
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
public class TelefonoClienteService {

    //Logica de negocio

    private final TelefonoCliente telefonoCliente;
    private final ObjectMapper objectMapper;
    private final ConvertidorJsonAResponseWrapper<Telefono> convertidorJsonAResponseWrapper;
    private final ConvertidorJsonAResponseWrapper<List<Telefono>> convertidorJsonAListaResponseWrapper;
    private final ConvertidorJsonAResponseWrapper<Void> convertidorJsonVoidResponseWrapper;


    @Autowired
    public TelefonoClienteService(TelefonoCliente telefonoCliente,
                                  ObjectMapper objectMapper,
                                  ConvertidorJsonAResponseWrapper<Telefono> convertidorJsonAResponseWrapper,
                                  ConvertidorJsonAResponseWrapper<List<Telefono>> convertidorJsonAListaResponseWrapper,
                                  ConvertidorJsonAResponseWrapper<Void> convertidorJsonVoidResponseWrapper
    ) {
        this.telefonoCliente = telefonoCliente;
        this.objectMapper = objectMapper;
        this.convertidorJsonAResponseWrapper = convertidorJsonAResponseWrapper;
        this.convertidorJsonAListaResponseWrapper = convertidorJsonAListaResponseWrapper;
        this.convertidorJsonVoidResponseWrapper = convertidorJsonVoidResponseWrapper;
    }

    //guardar telefono
    public Optional<Telefono> guardarTelefono(Telefono telefono){
        String telefonoNuevo = telefonoCliente.guardarTelefono(telefono);

        try {
            ResponseWrapper<Telefono> telefonoResponseWrapper = objectMapper.readValue(telefonoNuevo, new TypeReference<ResponseWrapper<Telefono>>(){});
            log.info(telefonoResponseWrapper.getMessage());
            return Optional.ofNullable(telefonoResponseWrapper.getResponseEntity().getBody());
        } catch (JsonProcessingException e) {
            log.warn("Erro al parsear la respuesta del servidor {}", e.getMessage());
            return Optional.empty();
        }
    }

    //obtener telefonos
    public Optional<List<Telefono>> obtenerTelefonos(){
        String telefonos = telefonoCliente.obtenerTelefonos();
        TypeReference<ResponseWrapper<List<Telefono>>> typeReference = new TypeReference<>(){};

        return convertidorJsonAListaResponseWrapper.respuestaAParsear(telefonos, typeReference);
    }

    //obtener Telefono por ID
    public Optional<Telefono> obtenerTelefonPorID(int idTelefono){
        String telefono = telefonoCliente.obtenerTelefonoPorID(idTelefono);
        TypeReference<ResponseWrapper<Telefono>> typeReference = new TypeReference<>(){};

        return convertidorJsonAResponseWrapper.respuestaAParsear(telefono, typeReference);
    }

    //actualizar Telefono por ID
    public Optional<Telefono> actualizarTelefonoPorID(int idTelefono, Telefono telefonoActualizado){
        String telefono = telefonoCliente.actualizarTelefonoPorID(idTelefono, telefonoActualizado);
        TypeReference<ResponseWrapper<Telefono>> typeReference = new TypeReference<>(){};

        return convertidorJsonAResponseWrapper.respuestaAParsear(telefono, typeReference);
    }

    //eliminar Telefono por ID
    public Optional<Void> eliminarTelefonoPorID(int idTelefono){
        String telefono = telefonoCliente.eliminarTelefonoPorId(idTelefono);
        TypeReference<ResponseWrapper<Void>> typeReference = new TypeReference<ResponseWrapper<Void>>(){};

        return convertidorJsonVoidResponseWrapper.respuestaAParsear(telefono, typeReference);
    }
}
