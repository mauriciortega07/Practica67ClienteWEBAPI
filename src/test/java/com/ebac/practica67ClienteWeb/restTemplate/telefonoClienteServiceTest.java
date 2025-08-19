package com.ebac.practica67ClienteWeb.restTemplate;

import com.ebac.practica67ClienteWeb.dto.Telefono;
import com.ebac.practica67ClienteWeb.dto.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class telefonoClienteServiceTest {

    @Autowired
    TelefonoClienteService telefonoClienteService;

    @Autowired
    UsuarioClienteService usuarioClienteService;


    @Test
    void guardarTelefono() {
        Usuario usuario = Usuario.builder()
                .nombre("Lore")
                .edad(56)
                .build();

        Optional<Usuario> usuarioOptional = usuarioClienteService.guardarUsuario(usuario);

        usuarioOptional.ifPresent(usuario1 -> {
            Telefono telefono = Telefono.builder()
                    .lada(55)
                    .tipo("Casa")
                    .numero("10102020")
                    .usuario(usuario1)
                    .build();
            Optional<Telefono> telefonoNuevo = telefonoClienteService.guardarTelefono(telefono);
            telefonoNuevo.ifPresent(System.out::println);
        });
    }

    @Test
    void obtenerTelefonos() {
        Optional<List<Telefono>> telefonosLista = telefonoClienteService.obtenerTelefonos();
        telefonosLista.get().forEach(v -> System.out.println(v));
    }

    @Test
    void obtenerTelefonPorID() {
        int idTelefono = 42;
        Optional<Telefono> telefono = telefonoClienteService.obtenerTelefonPorID(42);
        telefono.ifPresent(v -> System.out.println(v));
    }

    @Test
    void actualizarTelefonoPorID() {
        int idTelefono = 44;

        Telefono telefonoActualizado = Telefono.builder()
                .tipo("Sala de Concierto")
                .lada(23)
                .numero("888888")
                .build();

        Optional<Telefono> telefonoOptional = telefonoClienteService.actualizarTelefonoPorID(idTelefono, telefonoActualizado);
        telefonoOptional.ifPresent(v -> System.out.println(v));
    }

    @Test
    void eliminarTelefonoPorID() {

        int idTelefono = 42;

        Optional<Void> optional = telefonoClienteService.eliminarTelefonoPorID(idTelefono);

        optional.ifPresent(op -> System.out.println(op));
    }
}