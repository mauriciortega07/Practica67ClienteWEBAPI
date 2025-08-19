package com.ebac.practica67ClienteWeb.component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

import static java.util.Locale.filter;

public class AuthorizationFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("Inicio del filtro de autenticacion");
    }

    private boolean filter(String requestUri, String... resources){
        for(String resource : resources){
            if(requestUri.contains(resource)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        if(filter(request.getRequestURI(), "/css", "/js", "/login") && Objects.isNull(session.getAttribute("username"))){
            log.info(request.getRequestURI());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/pagina-login");

            dispatcher.forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
