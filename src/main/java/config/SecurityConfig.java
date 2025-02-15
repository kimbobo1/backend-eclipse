package config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
            
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
           
            .csrf(csrf -> csrf.disable())
            
            .authorizeHttpRequests(auth -> auth
                
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers("/login").permitAll()
                
               
                .requestMatchers(HttpMethod.GET, "/api/workout-goals/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/workout-plans/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/me").authenticated()  // 내 정보 조회

                
                .anyRequest().authenticated()
            )
          
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
       
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
       
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        configuration.setAllowedHeaders(Arrays.asList("*"));
       
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
