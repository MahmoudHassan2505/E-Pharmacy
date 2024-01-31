package com.banhauniversity.sidalih.security;

import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

     private UserRepository userRepository;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(UserRepository userRepo,
                          JwtTokenFilter jwtTokenFilter) {
        this.userRepository = userRepo;
        this.jwtTokenFilter = jwtTokenFilter;
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessage.Invalid_Credential)
                        )
                );
    }

    //this method to get users from Database and also get their roles
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){



        return new JdbcUserDetailsManager(dataSource);
    }

    //This method to add Roles Authorizations
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors(cors->cors.disable());

        // Set session management to stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        //adding roles Auth
        http.authorizeHttpRequests(configure->
                configure.requestMatchers(HttpMethod.GET,"/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/auth/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/pharmacy/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST,"/pharmacy/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/pharmacy/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/pharmacy/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .anyRequest().authenticated()
        );

        //to tell Spring that we use Basic Authentication
//        http.httpBasic(Customizer.withDefaults());

        //Disable CSRF (Cross Site Request Forgery)
        http.csrf(csrf->csrf.disable());

        // Add JWT token filter
        http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
