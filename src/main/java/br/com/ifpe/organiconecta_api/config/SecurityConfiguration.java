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

        public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
                        AuthenticationProvider authenticationProvider) {
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
                                                .requestMatchers(HttpMethod.POST, "/api/tipocliente/inicializar")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api-docs/*").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/administrador").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/produto").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/produto/*").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/loja").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/loja/*").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/redefinir/*").permitAll()


                                                // PERMISSÕES DE ACESSO DE CLIENTE
                                                .requestMatchers(HttpMethod.PUT, "/api/cliente/*").hasAnyAuthority(
                                                                Perfil.ROLE_CLIENTE,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.DELETE, "/api/cliente/*").hasAnyAuthority(
                                                                Perfil.ROLE_CLIENTE,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.GET, "/api/cliente").hasAnyAuthority(
                                                                Perfil.ROLE_CLIENTE,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.GET, "/api/cliente/*").hasAnyAuthority(
                                                                Perfil.ROLE_CLIENTE,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)


                                                // PERMISSÕES DE ACESSO DE PRODUTO
                                                .requestMatchers(HttpMethod.POST, "/api/produto").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.PUT, "/api/produto/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.DELETE, "/api/produto/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                // PERMISSÕES DE ACESSO DE TIPOCLIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/tipocliente").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                // PERMISSÕES DE ACESSO DE LOJAS
                                                .requestMatchers(HttpMethod.POST, "/api/loja").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.PUT, "/api/loja/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.DELETE, "/api/loja/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                

                                                // PERMISSÕES DE ACESSO DE ATIVAR/DESATIVAR PLANO
                                                .requestMatchers(HttpMethod.PUT, "/api/assinatura/{id}/ativarplano")
                                                .hasAnyAuthority(
                                                                Perfil.ROLE_CLIENTE,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.PUT, "/api/assinatura/{id}/desativarplano")
                                                .hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)
                                                                

                                                // PERMISSÕES DE ACESSO DE PEDIDO
                                                .requestMatchers(HttpMethod.POST, "/api/pedido").hasAnyAuthority(
                                                                Perfil.ROLE_CLIENTE,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.GET, "/api/pedido/*").hasAnyAuthority(
                                                                Perfil.ROLE_CLIENTE,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                                .requestMatchers(HttpMethod.DELETE, "/api/pedido/{clienteId}/cliente/{clienteId}")
                                                .hasAnyAuthority(
                                                                Perfil.ROLE_CLIENTE,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                                                Perfil.ROLE_FUNCIONARIO_USER,
                                                                Perfil.ROLE_CLIENTE_PRODUTOR)

                                              

                                                .anyRequest().authenticated()

                                )
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();

                // configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                configuration.setAllowedOriginPatterns(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
