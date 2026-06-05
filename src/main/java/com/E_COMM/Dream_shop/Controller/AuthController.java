package com.E_COMM.Dream_shop.Controller;

import com.E_COMM.Dream_shop.Response.ApiResponse;
import com.E_COMM.Dream_shop.Response.JwtResponse;
import com.E_COMM.Dream_shop.Security.JWT.JwtUtils;
import com.E_COMM.Dream_shop.Security.user.ShopUserDetails;
import com.E_COMM.Dream_shop.request.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils  jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try{
             Authentication authentication = authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
             );
             SecurityContextHolder.getContext().setAuthentication(authentication);
             String jwt = jwtUtils.generateToken(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);

            return ResponseEntity.ok(new ApiResponse("logged in successfully", jwtResponse));
        }
        catch (Exception e){
             e.printStackTrace();
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                     new ApiResponse("login failed", e.getMessage()));
        }
    }

}
