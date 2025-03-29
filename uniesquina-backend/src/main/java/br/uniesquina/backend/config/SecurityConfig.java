package br.uniesquina.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // desativa CSRF (recomendo manter ativado para POST com navegador)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/usuarios/hash-senha").permitAll() // LIBERADO
                        .anyRequest().authenticated()                // exige token para o resto
                )
                .httpBasic(Customizer.withDefaults()) // ou remova completamente se for usar JWT puro
                .formLogin(AbstractHttpConfigurer::disable);   // ⚠️ desativa a tela de login padrão

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // permite CORS em todos os endpoints
                        .allowedOrigins("*") // aceita requisições de qualquer origem
                        .allowedMethods("*") // aceita todos os métodos (GET, POST, etc.)
                        .allowedHeaders("*") // aceita todos os headers
                        .allowCredentials(false); // true se quiser enviar cookies/autenticação
            }
        };
    }

    // Aqui depois você pode adicionar:
    // - filtro JWT
    // - HttpSecurity
    // - AuthenticationManager, etc.
}
