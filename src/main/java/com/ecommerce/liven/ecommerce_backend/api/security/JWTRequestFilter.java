package com.ecommerce.liven.ecommerce_backend.api.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ecommerce.liven.ecommerce_backend.model.UsuarioLocal;
import com.ecommerce.liven.ecommerce_backend.model.dataAccesObject.UsuarioLocalDAO;
import com.ecommerce.liven.ecommerce_backend.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UsuarioLocalDAO usuarioLocalDAO;

    public JWTRequestFilter(JWTService jwtService, UsuarioLocalDAO usuarioLocalDAO) {
        this.jwtService = jwtService;
        this.usuarioLocalDAO = usuarioLocalDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader  = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);//Pula os 7 caracteres do bearer e o espaço
            try{
                String userEmail = jwtService.getUseEmail(token);
                Optional<UsuarioLocal> opUser = usuarioLocalDAO.findByEmailIgnoreCase(userEmail);
                if(opUser.isPresent()) {
                    UsuarioLocal usuario = opUser.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
                    //newArrayList é o placeholdder de inserção das permissões em implementações futuras
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch(JWTDecodeException ex){
                // Inserção de log em caso de não autorização
            }
        }
        filterChain.doFilter(request, response);
    }
}
