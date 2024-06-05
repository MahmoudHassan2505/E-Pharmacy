package com.banhauniversity.sidalih.controller;


import com.banhauniversity.sidalih.dto.Token;
import com.banhauniversity.sidalih.entity.User;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.security.JwtService;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class Auth {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtTokenUtil;

    public Auth(AuthenticationManager authenticationManager,
                JwtService jwtTokenUtil
                   ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;

    }

    @PostMapping("/login")
    public Token login(@RequestBody User user) {
        Authentication authenticate = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getUsername(), user.getPassword()
                            )
        );
        if (authenticate.isAuthenticated()) {
            Token token = new Token(jwtTokenUtil.generateToken(user.getUsername()),user.getUsername());
            return token;
        }
        throw new CustomException(ExceptionMessage.Invalid_Credential);
    }

    @GetMapping("/validate")
    public boolean validate(@RequestParam("token") String token){

       return jwtTokenUtil.validateToken(token);
    }

}
