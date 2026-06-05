package com.E_COMM.Dream_shop.Security.JWT;

import com.E_COMM.Dream_shop.Security.user.ShopUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private  final JwtUtils  jwtUtils;
    private  final  ShopUserDetailsService shopUserDetailsService;


    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,
                                    @NonNull  HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            System.out.println("Authorization Header = " +
                    request.getHeader("Authorization"));

            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateToken(jwt)) {

                String email = jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = shopUserDetailsService.loadUserByUsername(email);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                System.out.println("Authorities = " + userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }catch (JwtException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Invalid JWT Token\n"+e.getMessage());
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }


    private String parseJwt(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if(jwt != null && jwt.startsWith("Bearer ")) {
            return jwt.substring(7);
        }
        return null;
    }

}
