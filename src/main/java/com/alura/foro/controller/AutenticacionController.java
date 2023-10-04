package com.alura.foro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alura.foro.dto.DatosAutenticacionUsuario;
import com.alura.foro.dto.DatosRegistroUsuario;
import com.alura.foro.dto.DatosToken;
import com.alura.foro.modelo.Usuario;
import com.alura.foro.repository.UsuarioRepository;
import com.alura.foro.service.JWTService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
public class AutenticacionController {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AutenticacionController(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager, JWTService jwtService){
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Registro de nuevos usuarios - Acceso Libre")
    @PostMapping("/signup")
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario){
        String contrasena = passwordEncoder.encode(datosRegistroUsuario.contrasena());
        Usuario usuario = new Usuario(datosRegistroUsuario.nombre(), datosRegistroUsuario.email(), contrasena);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @Operation(summary = "Autenticación de usuarios y generación de Token - Acceso Libre")
    @PostMapping("/login")
    public ResponseEntity<DatosToken> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        Authentication usuarioAutenticable = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(), datosAutenticacionUsuario.contrasena());
        Authentication usuarioAutenticado = authenticationManager.authenticate(usuarioAutenticable);
        String jwt = jwtService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        DatosToken datosToken = new DatosToken(jwt);
        return ResponseEntity.ok(datosToken);
    }
    
}
