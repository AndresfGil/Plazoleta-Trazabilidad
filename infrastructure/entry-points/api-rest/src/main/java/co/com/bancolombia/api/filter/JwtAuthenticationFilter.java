package co.com.bancolombia.api.filter;

import co.com.bancolombia.api.service.JwtValidationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtValidationService jwtValidationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String token = extractTokenFromRequest(request);
            
            if (token != null && jwtValidationService.validateToken(token)) {
                String correo = jwtValidationService.getCorreoFromToken(token);
                String rol = jwtValidationService.getRolFromToken(token);
                Long idUsuario = jwtValidationService.getIdUsuarioFromToken(token);
                
                if (correo != null && rol != null && idUsuario != null) {
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rol));
                    UsernamePasswordAuthenticationToken auth = 
                        new UsernamePasswordAuthenticationToken(correo, null, authorities);
                    
                    auth.setDetails(idUsuario);
                    
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    
                    log.debug("Usuario autenticado: {} con rol: {} e ID: {}", correo, rol, idUsuario);
                } else {
                    log.warn("Token válido pero faltan datos requeridos - correo: {}, rol: {}, idUsuario: {}", 
                            correo, rol, idUsuario);
                }
            }
        } catch (Exception e) {
            log.error("Error en autenticación: {}", e.getMessage());
        }
        
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        
        return authHeader.substring(7);
    }
}
