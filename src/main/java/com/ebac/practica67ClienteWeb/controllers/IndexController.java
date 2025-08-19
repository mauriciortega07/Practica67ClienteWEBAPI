package com.ebac.practica67ClienteWeb.controllers;

import com.ebac.practica67ClienteWeb.dto.*;
import com.ebac.practica67ClienteWeb.restTemplate.UsuarioClienteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.ResponseEntity;

import java.util.*;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    UsuarioClienteService usuarioClienteService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object index() {
        log.info("Ingresando a la pagina principal");
        ModelAndView modelAndView = new ModelAndView("index");


        Optional<List<Usuario>> listOptional = usuarioClienteService.obtenerUsuarios();
        List<Usuario> usuariosList = new ArrayList<>();

        if (listOptional.isPresent()) {
            usuariosList = listOptional.get();
        }

        modelAndView.addObject("numUsuario", usuariosList.size());
        modelAndView.addObject("usuarios", usuariosList);

        return modelAndView;
    }

    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public Object informacionUsuario(HttpServletRequest request) {
        log.info("Obteniendo info del usuario");
        ModelAndView modelAndView = new ModelAndView("infoUsuario");
        String idUsuario = request.getParameter("idUsuario");

        if (!Objects.isNull(idUsuario) && !idUsuario.isEmpty()) {
            Optional<Usuario> usuarioOptional = usuarioClienteService.obtenerUsuarioPorID(Integer.parseInt(idUsuario));

            usuarioOptional.ifPresent(usuario -> modelAndView.addObject("usuario", usuario));
        }

        return modelAndView;
    }

    @RequestMapping(value = "/formulario-usuario", method = RequestMethod.GET)
    public Object formularioUsuario(HttpServletRequest request) {

        String idUsuario = request.getParameter("idUsuario");

        Usuario usuario = Usuario.crearUsuarioVacio();
        List<Telefono> telefonos = usuario.getTelefonos();

        ModelAndView modelAndView = new ModelAndView("formulario-usuario");
        modelAndView.addObject("propositoFormulario", "Crear usuario");

        //Si el idUsuario no es nulo y no esta vacio
        if (!Objects.isNull(idUsuario) && !idUsuario.isEmpty()) {
            Optional<Usuario> usuarioOptional = usuarioClienteService.obtenerUsuarioPorID(Integer.parseInt(idUsuario));

            if (usuarioOptional.isPresent()) {
                usuario = usuarioOptional.get();

                if (!usuario.getTelefonos().isEmpty()) {
                    telefonos = usuario.getTelefonos();
                }

                modelAndView.addObject("propositoFormulario", "Actualizar usuario");
            }
        }

        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("telefonos", telefonos);

        return modelAndView;
    }

    @RequestMapping(value = "/guardar-usuario", method = RequestMethod.POST)
    public ResponseEntity<?> guardarUsuario(HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.OK;
        Usuario usuarioCreado = new Usuario();

        try {
            String usuarioId = request.getParameter("FormUsuarioId");
            String usuarioNombre = request.getParameter("FormUsuarioNombre");
            String usarioEdad = request.getParameter("FormUsuarioEdad");

            String telefonoNumero = request.getParameter("FormTelefonoNumero");
            String telefonoLada = request.getParameter("FormTelefonoLada");
            String telefonoTipo = request.getParameter("FormTelefonoTipo");

            Usuario.UsuarioBuilder usuarioBuilder = Usuario.builder()
                    .nombre(usuarioNombre)
                    .edad(Integer.parseInt(usarioEdad)
                    );

            if (usuarioId != null && !usuarioId.isBlank() && !usuarioId.equals("0")) {
                /*en caso de que el id no sea cero, se trata de una actualizacion,
                entonces se le agrega el id que no es 0, a la variable usuarioId
                */
                usuarioBuilder.idUsuario(Integer.parseInt(usuarioId));
            }

            Telefono telefono = Telefono.builder()
                    .tipo(telefonoTipo)
                    .lada(Integer.parseInt(telefonoLada))
                    .numero(telefonoNumero)
                    .build();

            usuarioBuilder.telefonos(List.of(telefono));

            Usuario usuario = usuarioBuilder.build();

            Optional<Usuario> usuarioOptional = usuarioClienteService.guardarUsuario(usuario);


            if (usuarioOptional.isPresent()) {
                usuarioCreado = usuarioOptional.get();
                return ResponseEntity.ok(Map.of(
                        "message", "Usuario Creado Correctamente",
                        "usuario", usuarioCreado
                ));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "usuario ya existente"));

            }


        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al guardar el usuario", "error", e.getMessage()));
        }


    }

}
