package com.ebac.practica67ClienteWeb.restTemplate;


import com.ebac.practica67ClienteWeb.dto.ConvertidorJsonAResponseWrapper;
import com.ebac.practica67ClienteWeb.dto.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class usuarioClienteServiceTest {

    @Autowired
    private UsuarioCliente usuarioCliente;

    @Autowired
    private  ObjectMapper objectMapper;

    @Autowired
    private UsuarioClienteService usuarioClienteService;

    @Autowired
    private ConvertidorJsonAResponseWrapper convertidorJsonAResponseWrapper;

    @BeforeAll
    static void init() {
        RestTemplate restTemplate = new RestTemplate();
    }

    @Test
    void guardarUsuario() {
        Usuario usuario = Usuario.builder().nombre("alm").edad(5).build();
        usuarioClienteService.guardarUsuario(usuario);
    }

    @Test
    void obtenerUsuarios(){
        List<Usuario> usuarioList = usuarioClienteService.obtenerUsuarios().get();
        usuarioList.forEach(System.out::println);
    }

    @Test
    void obtenerUsuariosPorID(){
        int idUsuario = 10;
        Usuario usarioObtenido = usuarioClienteService.obtenerUsuarioPorID(10).get();
        System.out.println(usarioObtenido);
    }

    @Test
    void actualizaUsuario(){
        int idUsuario = 11;
        Usuario usuario = Usuario.builder()
                .nombre("Paul McCartney")
                .edad(19)
                .build();
        Usuario usuarioActualizado = usuarioClienteService.actualizarPorID(idUsuario, usuario).get();
        System.out.println(usuarioActualizado);
    }

    @Test
    void eliminarUsuario(){
        int idUsuario = 38;
        Optional<Void> usuarioOptional = usuarioClienteService.eliminarUsuarioPorID(idUsuario);
        if(usuarioOptional.isPresent()){
            System.out.println(usuarioOptional.get());
        } else {
            System.out.println("El usuario no existe");
        }
    }

}