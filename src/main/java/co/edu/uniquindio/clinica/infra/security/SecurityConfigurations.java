package co.edu.uniquindio.clinica.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static co.edu.uniquindio.clinica.model.Roles.ADMIN;
import static co.edu.uniquindio.clinica.model.Roles.PACIENTE;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;
    private static final String[] adminUrls =
            {
                    "/api/medicos/{id}/crear",
                    "/api/medicos/{id}/actualizar",
                    "/api/medicos/{id}/eliminar",
                    "/api/medicos/{id}/listar",
                    "/api/medicos/find"
            };
    private static final String[] pacienteUrls =
            {
                    "/api/auth/**",
                    "/api/productos/obtener/**",
                    "/api/productos/listar/todos",
                    "/api/productos/listar/nombre/**",
                    "/api/productos/listar/categoria/**",
                    "/api/categorias/**",
                    "/api/mediospago/**",
                    "/api/comentarios/listar/**"
            };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req->
                    req
                            .requestMatchers(HttpMethod.POST, "/login").permitAll()
                            .requestMatchers(adminUrls).hasRole(ADMIN.name())//diferencia con hasAuthority
                            .requestMatchers(pacienteUrls).hasAnyRole(PACIENTE.name(), ADMIN.name())
                            .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
