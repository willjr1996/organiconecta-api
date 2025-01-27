package br.com.ifpe.organiconecta_api.config;


import java.util.Arrays;
import java.util.List;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import br.com.ifpe.organiconecta_api.modelo.acesso.Perfil;
import br.com.ifpe.organiconecta_api.modelo.acesso.Usuario;
import br.com.ifpe.organiconecta_api.modelo.seguranca.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(c -> c.disable())
            .authorizeHttpRequests(authorize -> authorize


                .requestMatchers(HttpMethod.POST, "/api/cliente").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/tipocliente/inicializar").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/tipocliente").permitAll()
                .requestMatchers(HttpMethod.GET, "/api-docs/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()


                // PERMISSÕES DE ACESSO DE PRODUTO
                .requestMatchers("/api/produto").hasAnyAuthority(
                   Perfil.ROLE_FUNCIONARIO_ADMIN,
                   Perfil.ROLE_CLIENTE_PRODUTOR)

                   .requestMatchers(HttpMethod.GET, "/api/produto").hasAnyAuthority(
                   Perfil.ROLE_CLIENTE,
                   Perfil.ROLE_FUNCIONARIO_ADMIN,
                   Perfil.ROLE_CLIENTE_PRODUTOR)

                   .requestMatchers(HttpMethod.GET, "/api/produto/*").hasAnyAuthority(
                   Perfil.ROLE_CLIENTE,
                   Perfil.ROLE_FUNCIONARIO_ADMIN,
                   Perfil.ROLE_CLIENTE_PRODUTOR)

                
                // PERMISSÕES DE ACESSO DE PRODUTO
                .requestMatchers(HttpMethod.GET, "/api/tipocliente").hasAnyAuthority(              
                    Perfil.ROLE_FUNCIONARIO_ADMIN)
               


                // PERMISSÕES DE ACESSO DE LOJAS
                .requestMatchers("/api/lojas").hasAnyAuthority(
                   Perfil.ROLE_FUNCIONARIO_ADMIN,
                   Perfil.ROLE_CLIENTE_PRODUTOR)

                   .requestMatchers(HttpMethod.GET, "/api/lojas").hasAnyAuthority(
                    Perfil.ROLE_CLIENTE,
                    Perfil.ROLE_FUNCIONARIO_ADMIN,
                    Perfil.ROLE_CLIENTE_PRODUTOR)
 
                    .requestMatchers(HttpMethod.GET, "/api/lojas/*").hasAnyAuthority(
                    Perfil.ROLE_CLIENTE,
                    Perfil.ROLE_FUNCIONARIO_ADMIN,
                    Perfil.ROLE_CLIENTE_PRODUTOR)


               // PERMISSÕES DE ACESSO DE REDEFINIR SENHA
                .requestMatchers("/api/redefinir").hasAnyAuthority(
                   Perfil.ROLE_CLIENTE,
                   Perfil.ROLE_FUNCIONARIO_ADMIN,
                   Perfil.ROLE_CLIENTE_PRODUTOR)
               
                

                

               
                .requestMatchers(HttpMethod.POST,"/api/lojas").permitAll()
                // .requestMatchers(HttpMethod.POST, "/api/lojas").hasAnyAuthority(
                //    Perfil.ROLE_FUNCIONARIO_ADMIN,
                //     Perfil.ROLE_CLIENTE_PRODUTOR)


                .requestMatchers(HttpMethod.GET, "/api/assinatura/{id}/ativarplano").permitAll()
                // .requestMatchers(HttpMethod.GET, "/api/lojas/").hasAnyAuthority(
                //    Perfil.ROLE_CLIENTE,
                //    Perfil.ROLE_FUNCIONARIO_ADMIN,
                //    Perfil.ROLE_CLIENTE_PRODUTOR)

                .requestMatchers(HttpMethod.GET, "/api/assinatura/{id}/desativarplano").permitAll()
                // .requestMatchers(HttpMethod.GET, "/api/lojas/").hasAnyAuthority(
                //    Perfil.ROLE_FUNCIONARIO_ADMIN,
                //    Perfil.ROLE_CLIENTE_PRODUTOR)

                .requestMatchers(HttpMethod.POST, "/api/pedido").permitAll()
                // .requestMatchers(HttpMethod.GET, "/api/lojas/").hasAnyAuthority(
                //    Perfil.ROLE_CLIENTE,
                //    Perfil.ROLE_FUNCIONARIO_ADMIN,
                //    Perfil.ROLE_CLIENTE_PRODUTOR)

                //.requestMatchers(HttpMethod.POST, "/api/pedido").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/pedido/cliente/*").hasAnyAuthority(
                   Perfil.ROLE_CLIENTE,
                   Perfil.ROLE_FUNCIONARIO_ADMIN,
                   Perfil.ROLE_CLIENTE_PRODUTOR)

                   .requestMatchers(HttpMethod.DELETE, "/api/pedido/cliente/*").hasAnyAuthority(
                   Perfil.ROLE_CLIENTE,
                   Perfil.ROLE_FUNCIONARIO_ADMIN,
                   Perfil.ROLE_CLIENTE_PRODUTOR)
             
               








                .anyRequest().authenticated()


            )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )            
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


    public CorsConfigurationSource corsConfigurationSource() {


        CorsConfiguration configuration = new CorsConfiguration();


        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
   
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);    
        return source;
    }
}
