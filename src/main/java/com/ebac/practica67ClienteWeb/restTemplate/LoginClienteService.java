package com.ebac.practica67ClienteWeb.restTemplate;

import com.ebac.practica67ClienteWeb.dto.ConvertidorJsonAResponseWrapper;
import com.ebac.practica67ClienteWeb.dto.Login;
import com.ebac.practica67ClienteWeb.dto.ResponseWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LoginClienteService {

    private final ObjectMapper objectMapper;
    private final LoginCliente loginCliente;
    private final ConvertidorJsonAResponseWrapper<Login> convertidorJsonAResponseWrapper;
    private final ConvertidorJsonAResponseWrapper<List<Login>> convertidorJsonAListResponseWrapper;
    private final ConvertidorJsonAResponseWrapper<Void> convertidorJsonAVoidResponseWrapper;

    @Autowired
    public LoginClienteService(ObjectMapper objectMapper,
                               LoginCliente loginCliente,
                               ConvertidorJsonAResponseWrapper<Login> convertidorJsonAResponseWrapper,
                               ConvertidorJsonAResponseWrapper<List<Login>> convertidorJsonAListResponseWrapper,
                               ConvertidorJsonAResponseWrapper<Void> convertidorJsonAVoidResponseWrapper
    ) {
        this.objectMapper = objectMapper;
        this.loginCliente = loginCliente;
        this.convertidorJsonAResponseWrapper = convertidorJsonAResponseWrapper;
        this.convertidorJsonAListResponseWrapper = convertidorJsonAListResponseWrapper;
        this.convertidorJsonAVoidResponseWrapper = convertidorJsonAVoidResponseWrapper;
    }

    public Optional<Login> guardarLogin(Login login){
        String loginNuevo = loginCliente.guardarLogin(login);
        TypeReference<ResponseWrapper<Login>> typeReference = new TypeReference<>(){};
        return convertidorJsonAResponseWrapper.respuestaAParsear(loginNuevo, typeReference);
    }

    public Optional<List<Login>> obtenerLogins(){
        String loginsObtenidos = loginCliente.obtenerLogins();

        TypeReference<ResponseWrapper<List<Login>>> typeReference = new TypeReference<>(){};

        return convertidorJsonAListResponseWrapper.respuestaAParsear(loginsObtenidos, typeReference);
    }

    public Optional<Login> obtenerLoginPorID(int idLogin) {
        String loginPorID = loginCliente.obtenerLoginPorID(idLogin);
        TypeReference<ResponseWrapper<Login>> typeReference = new TypeReference<>() {};

        return convertidorJsonAResponseWrapper.respuestaAParsear(loginPorID, typeReference);
    }

    public Optional<Login> actualizarPorID(int idLogin, Login loginAct){
        String loginActualizado = loginCliente.actualizarPorID(idLogin, loginAct);
        TypeReference<ResponseWrapper<Login>> typeReference = new TypeReference<>() {};

        return convertidorJsonAResponseWrapper.respuestaAParsear(loginActualizado, typeReference);
    }

    public Optional<Void> eliminarPorID(int idLogin){
        String loginEliminado = loginCliente.eliminarPorID(idLogin);
        TypeReference<ResponseWrapper<Void>> typeReference = new TypeReference<>(){};

        return convertidorJsonAVoidResponseWrapper.respuestaAParsear(loginEliminado, typeReference);
    }
}
