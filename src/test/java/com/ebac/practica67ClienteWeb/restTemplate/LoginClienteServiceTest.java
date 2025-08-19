package com.ebac.practica67ClienteWeb.restTemplate;

import com.ebac.practica67ClienteWeb.dto.Login;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginClienteServiceTest {

    @Autowired
    LoginClienteService loginClienteService;

    @Test
    void guardarLogin() {
        Login login = Login.builder()
                .nombre("Martha")
                .edad(10)
                .email("marha@mail.com")
                .password("12we")
                .build();

        Optional<Login> loginNuevo = loginClienteService.guardarLogin(login);
        loginNuevo.ifPresent(System.out::println);
    }

    @Test
    void obtenerLogins() {
        Optional<List<Login>> logins = loginClienteService.obtenerLogins();

        if(logins.isPresent()){
            List<Login> loginList = logins.get();
            loginList.forEach(System.out::println);
        }
    }

    @Test
    void obtenerLoginPorID() {
        int idLogin = 12;
        Optional<Login> loginObtenido = loginClienteService.obtenerLoginPorID(idLogin);

        loginObtenido.ifPresent(v -> {
            System.out.println(v);
        });
    }

    @Test
    void actualizarPorID() {
        int idLogin = 1;
        Login loginActualizado = Login.builder()
                .nombre("Mauricio Ortega")
                .edad(25)
                .email("elragtis@gmail.com")
                .password("baba1")
                .build();

        Optional<Login> loginAct = loginClienteService.actualizarPorID(idLogin, loginActualizado);

        loginAct.ifPresent(login -> {
            System.out.println(login);
        });
    }

    @Test
    void eliminarPorID() {
        int idLogin = 6;
        Optional<Void> loginVoid = loginClienteService.eliminarPorID(6);

        loginVoid.ifPresent(v -> {
            System.out.println(v);
        });
    }
}