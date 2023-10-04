package com.alura.foro.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alura.foro.modelo.Respuesta;
import com.alura.foro.modelo.Topico;
import com.alura.foro.modelo.Usuario;
import com.alura.foro.repository.RespuestaRepository;
import com.alura.foro.repository.TopicoRepository;
import com.alura.foro.repository.UsuarioRepository;
import com.alura.foro.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("jwtFilter")
public class JWTFilter extends OncePerRequestFilter{

    private final JWTService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final TopicoRepository topicoRepository;
    private final RespuestaRepository respuestaRepository;

    @Value("${api.security.admin.username}")
    private String admin;

    public JWTFilter(JWTService jwtService, UsuarioRepository usuarioRepository, TopicoRepository topicoRepository, RespuestaRepository respuestaRepository){
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.topicoRepository = topicoRepository;
        this.respuestaRepository = respuestaRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header == null || header.isBlank() || !header.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer", "").trim();

        if(token == null || token.isBlank() || !jwtService.verificarToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.getSubject(token);

        if(!usuarioRepository.existsByEmail(email)){
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails usuario = usuarioRepository.findByEmail(email);

        Authentication usuarioAutenticado;
        if(usuario.getUsername().equals(admin)){
            usuarioAutenticado = new UsernamePasswordAuthenticationToken(usuario, null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER")));
        } else {
            usuarioAutenticado = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        }
        SecurityContextHolder.getContext().setAuthentication(usuarioAutenticado);
        filterChain.doFilter(request, response);
    }

    
    public boolean isTopicAuthor(Long id){
        if(isAdmin()){
            return true;
        }
        Topico topico = topicoRepository.getReferenceById(id);
        Long autorId = topico.getAutor().getId();
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuario.getId().equals(autorId);
    }

    
    public boolean isResponseAuthor(Long id){
        if(isAdmin()){
            return true;
        }
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        Long autorId = respuesta.getAutor().getId();
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuario.getId().equals(autorId);
    }

    private boolean isAdmin(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    
}
