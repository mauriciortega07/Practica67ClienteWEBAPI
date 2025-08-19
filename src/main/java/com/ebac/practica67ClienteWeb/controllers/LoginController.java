package com.ebac.practica67ClienteWeb.controllers;

import com.ebac.practica67ClienteWeb.dto.Login;
import com.ebac.practica67ClienteWeb.restTemplate.LoginClienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    LoginClienteService loginClienteService;

    //Inicio de Sesion
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pagina-login");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<List<Login>> logins = loginClienteService.obtenerLogins();

        if (logins.isPresent()){
            List<Login> loginList = logins.get();

            for (Login login : loginList){

                if(username.equalsIgnoreCase(login.getNombre()) && password.equals(login.getPassword())){
                    request.getSession().setAttribute("username", username);
                    log.info("Se inicia sesion con usuario....{}", login);
                    modelAndView.setViewName("redirect:/");
                    return modelAndView;
                }
            }

            log.error("Login no existente en la base de datos");
            modelAndView.addObject("error", "Usuario no registrado, o datos incorrectos");
            modelAndView.setViewName("pagina-login");
        }

        return modelAndView;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().invalidate();
        return "redirect:/pagina-login";
    }
}
